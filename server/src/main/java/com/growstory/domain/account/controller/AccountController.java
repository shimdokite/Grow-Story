package com.growstory.domain.account.controller;

import com.growstory.domain.account.dto.AccountDto;
import com.growstory.domain.account.service.AccountService;
import com.growstory.global.constants.HttpStatusCode;
import com.growstory.global.response.SingleResponseDto;
import com.growstory.global.utils.UriCreator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@Validated
@RequiredArgsConstructor
@Tag(name = "Account", description = "Account Controller")
@RequestMapping("/v1/accounts")
public class AccountController {
    private static final String ACCOUNT_DEFAULT_URL = "/v1/accounts";

    private final AccountService accountService;

    @Operation(summary = "회원가입", description = "사용자 정보를 입력받아 계정 생성")
    @PostMapping("/signup")
    public ResponseEntity<HttpStatus> postAccount(@Valid @RequestBody AccountDto.Post accountPostDto) {
        AccountDto.Response accountResponseDto = accountService.createAccount(accountPostDto);
        URI location = UriCreator.createUri(ACCOUNT_DEFAULT_URL, accountResponseDto.getAccountId());

        return ResponseEntity.created(location).build();
    }

    @Operation(summary = "프로필 사진 수정", description = "입력받은 프로필 사진으로 정보 수정")
    @PatchMapping(value = "/profileimage", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<HttpStatus> patchProfileImage(@RequestPart MultipartFile profileImage) {
        accountService.updateProfileImage(profileImage);

        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "닉네임 수정", description = "입력받은 닉네임으로 정보 수정")
    @PatchMapping("/displayname")
    public ResponseEntity<HttpStatus> patchDisplayName(@Valid @RequestBody AccountDto.DisplayNamePatch displayNamePatchDto) {
        accountService.updateDisplayName(displayNamePatchDto);

        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "비밀번호 수정", description = "입력받은 비밀번호로 정보 수정")
    @PatchMapping("/password")
    public ResponseEntity<HttpStatus> patchDisplayName(@Valid @RequestBody AccountDto.PasswordPatch passwordPatchDto) {
        accountService.updatePassword(passwordPatchDto);

        return ResponseEntity.noContent().build();
    }


    @Operation(summary = "나의 계정 조회", description = "로그인된 사용자의 정보 조회")
    @GetMapping
    public ResponseEntity<SingleResponseDto<AccountDto.Response>> getAccount() {
        AccountDto.Response responseDto = accountService.getAccount();

        return ResponseEntity.ok(SingleResponseDto.<AccountDto.Response>builder()
                .status(HttpStatusCode.OK.getStatusCode())
                .message(HttpStatusCode.OK.getMessage())
                .data(responseDto)
                .build()
        );
    }

    @Operation(summary = "전체 계정 조회", description = "전체 계정 정보 조회")
    @GetMapping("/all")
    public ResponseEntity<SingleResponseDto<List<AccountDto.Response>>> getAccounts() {
        List<AccountDto.Response> responseDtos = accountService.getAccounts();

        return ResponseEntity.ok(SingleResponseDto.<List<AccountDto.Response>>builder()
                .status(HttpStatusCode.OK.getStatusCode())
                .message(HttpStatusCode.OK.getMessage())
                .data(responseDtos)
                .build()
        );
    }

    @Operation(summary = "비밀번호 검증", description = "회원탈퇴시 비밀번호 검증")
    @PostMapping("/password/verification")
    public ResponseEntity<SingleResponseDto<Boolean>> verifyPassword(@Valid @RequestBody AccountDto.PasswordVerify passwordVerifyDto) {
        Boolean isMatched = accountService.verifyPassword(passwordVerifyDto);

        return ResponseEntity.ok(SingleResponseDto.<Boolean>builder()
                .status(HttpStatusCode.OK.getStatusCode())
                .message(HttpStatusCode.OK.getMessage())
                .data(isMatched)
                .build());
    }

    @Operation(summary = "회원탈퇴", description = "로그인된 사용자 계정 탈퇴")
    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteAccount() {
        accountService.deleteAccount();

        return ResponseEntity.noContent().build();
    }
}
