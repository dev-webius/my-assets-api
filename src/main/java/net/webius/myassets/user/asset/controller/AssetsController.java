package net.webius.myassets.user.asset.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.webius.myassets.user.asset.dto.*;
import net.webius.myassets.user.asset.service.AssetsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.UUID;

@Tag(name = "User")
@Tag(name = "Assets")
@RestController @RequiredArgsConstructor
@RequestMapping("/v1/assets")
public class AssetsController {
    private final AssetsService assetsService;

    @GetMapping
    @Operation(summary = "자산 목록 표시")
    @ApiResponse(responseCode = "200", description = "성공")
    public ResponseEntity<AssetsRes> getAssets() {
        var response = assetsService.getAssets();
        return ResponseEntity.ok().body(response);
    }

    @PostMapping
    @Operation(summary = "자산 등록")
    @ApiResponse(responseCode = "201", description = "성공")
    public ResponseEntity<Void> createAsset(@Valid @RequestBody AssetCreationReq assetCreationReq) throws URISyntaxException {
        var location = assetsService.createAsset(assetCreationReq);
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "자산 상세")
    @ApiResponse(responseCode = "200", description = "성공")
    public ResponseEntity<AssetDetailRes> getAsset(@PathVariable UUID id) {
        var response = assetsService.getAsset(id);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "자산 수정")
    @ApiResponse(responseCode = "204", description = "성공")
    public ResponseEntity<Void> applyAsset(@PathVariable UUID id, @Valid @RequestBody AssetApplicationReq assetApplicationReq) {
        assetsService.applyAsset(id, assetApplicationReq);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "자산 삭제")
    @ApiResponse(responseCode = "204", description = "성공")
    public ResponseEntity<Void> deleteAsset(@PathVariable UUID id) {
        assetsService.deleteAsset(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/options")
    @Operation(summary = "자산 등록 옵션 표시")
    @ApiResponse(responseCode = "200", description = "성공")
    public ResponseEntity<AssetCreationOptionsRes> getAssetsCreationOptions() {
        var response = assetsService.getAssetCreationOptions();
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/statistics")
    @Operation(summary = "자산 전체 통계 표시")
    @ApiResponse(responseCode = "200", description = "성공")
    public ResponseEntity<Void> getAssetsStatistics() {
        return ResponseEntity.internalServerError().build();
    }

    @PostMapping("/{id}/usage")
    @Operation(summary = "자산 사용량 업데이트")
    @ApiResponse(responseCode = "201", description = "성공")
    public ResponseEntity<Void> getAssetUsage(@PathVariable UUID id, @Valid @RequestBody AssetUsageReq assetUsageReq) {
        assetsService.updateAssetUsage(id, assetUsageReq);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{id}/statistics")
    @Operation(summary = "자산 통계 표시")
    @ApiResponse(responseCode = "200", description = "성공")
    public ResponseEntity<Void> getAssetStatistics(@PathVariable UUID id) {
        return ResponseEntity.internalServerError().build();
    }

    @GetMapping("/{id}/installments")
    @Operation(summary = "자산 할부 표시")
    @ApiResponse(responseCode = "200", description = "성공")
    public ResponseEntity<Void> getAssetInstallments(@PathVariable UUID id) {
        return ResponseEntity.internalServerError().build();
    }
}
