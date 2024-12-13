package net.webius.myassets.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import net.webius.myassets.exception.JwtExpiredException;
import net.webius.myassets.exception.JwtInvalidClaimException;
import net.webius.myassets.exception.JwtSignatureFailureException;
import net.webius.myassets.global.auth.exception.UsernameNotFoundException;
import net.webius.myassets.global.auth.service.JwtService;
import net.webius.myassets.global.auth.service.UserEntityUserDetailsService;
import net.webius.myassets.properties.JwtProperties;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component @RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final JwtProperties jwtProperties;
    private final UserEntityUserDetailsService userEntityUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
            throws JwtSignatureFailureException, JwtInvalidClaimException, JwtExpiredException, ServletException, IOException {
        String accessToken = request.getHeader(jwtProperties.accessToken().headerName());

        // Access Token 이 존재 하는 경우에만 파싱하여 점검
        if (accessToken != null) {
            try {
                var parsedAccessToken = jwtService.extractToken(accessToken);

                // 토큰 만료 검증
                jwtService.verifyTokenExpired(parsedAccessToken);

                // 페이로드
                var payload = parsedAccessToken.getPayload();

                // Access Token 제목 검증
                if (!jwtProperties.accessToken().subject().equals(payload.getSubject())) {
                    throw new JwtInvalidClaimException("Subject is " + payload.getSubject());
                }

                // 사용자 정보 수집
                String username = payload.get("username", String.class);
                var userDetails = userEntityUserDetailsService.loadUserByUsername(username);

                // 인증 정보 설정
                var authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (JwtExpiredException ignored) {
                // Access Token Refresh...? 체크 필요
//                String refreshToken = request.getHeader(jwtProperties.refreshToken().headerName());
//                var refreshTokenClaims = jwtService.extractToken(refreshToken);
//                jwtService.verifyTokenExpired(refreshTokenClaims);

            } catch (UsernameNotFoundException ignored) {
                throw new JwtInvalidClaimException("User not found");
            }
        }

        // 다음 필터 진행
        filterChain.doFilter(request, response);
    }
}
