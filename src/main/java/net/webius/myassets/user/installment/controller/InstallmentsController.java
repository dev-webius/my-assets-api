package net.webius.myassets.user.installment.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.webius.myassets.user.installment.dto.InstallmentApplicationReq;
import net.webius.myassets.user.installment.dto.InstallmentCreationReq;
import net.webius.myassets.user.installment.dto.InstallmentDetailRes;
import net.webius.myassets.user.installment.dto.InstallmentsRes;
import net.webius.myassets.user.installment.service.InstallmentsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "User")
@Tag(name = "Installment")
@RestController @RequiredArgsConstructor
@RequestMapping("/v1/installments")
public class InstallmentsController {
    private final InstallmentsService installmentsService;

    @PostMapping
    @Operation(summary = "할부 등록")
    @ApiResponse(responseCode = "201", description = "성공")
    public ResponseEntity<Void> addInstallment(@Valid @RequestBody InstallmentCreationReq installmentCreationReq) {
        var location = installmentsService.addInstallment(installmentCreationReq);
        return ResponseEntity.created(location).build();
    }

    @GetMapping
    @Operation(summary = "할부 목록 조회")
    @ApiResponse(responseCode = "200", description = "성공")
    public ResponseEntity<InstallmentsRes> getInstallments() {
        var response = installmentsService.getInstallments();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "할부 상세 조회")
    @ApiResponse(responseCode = "200", description = "성공")
    public ResponseEntity<InstallmentDetailRes> getInstallment(@PathVariable UUID id) {
        var response = installmentsService.getInstallment(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "할부 수정")
    @ApiResponse(responseCode = "204", description = "성공")
    public ResponseEntity<Void> applyInstallment(@PathVariable UUID id, @Valid @RequestBody InstallmentApplicationReq installmentApplicationReq) {
        installmentsService.applyInstallment(id, installmentApplicationReq);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "할부 삭제")
    @ApiResponse(responseCode = "204", description = "성공")
    public ResponseEntity<Void> deleteInstallment(@PathVariable UUID id) {
        installmentsService.deleteInstallment(id);
        return ResponseEntity.noContent().build();
    }
}
