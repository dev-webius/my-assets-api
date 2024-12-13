package net.webius.myassets.user.my.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.webius.myassets.user.my.dto.MyInfoApplicationReq;
import net.webius.myassets.user.my.dto.MyInfoRes;
import net.webius.myassets.user.my.service.MyInfoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "User")
@Tag(name = "My")
@RestController @RequiredArgsConstructor
@RequestMapping("/v1/my/info")
public class MyInfoController {
    private final MyInfoService myInfoService;

    @GetMapping
    @Operation(summary = "내 정보 불러오기")
    @ApiResponse(responseCode = "200", description = "성공")
    @ApiResponse(responseCode = "401", description = "권한 없음")
    public ResponseEntity<MyInfoRes> getMyInfo() {
        var myInfo = myInfoService.getMyInfo();
        return ResponseEntity.ok(myInfo);
    }

    @PutMapping
    @Operation(summary = "내 정보 수정하기")
    @ApiResponse(responseCode = "204", description = "성공")
    public ResponseEntity<Void> applyMyInfo(@Valid @RequestBody MyInfoApplicationReq myInfoApplicationReq) {
        myInfoService.applyMyInfo(myInfoApplicationReq);
        return ResponseEntity.noContent().build();
    }
}
