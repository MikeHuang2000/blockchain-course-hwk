package com.wetech.demo.web3j.controller;

import com.wetech.demo.web3j.service.EscrowService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Slf4j
@RestController
@RequestMapping("/api/escrow")
@RequiredArgsConstructor
public class EscrowController {

    private final EscrowService escrowService;

    /**
     * 部署 Escrow,仲裁人为后端账户
     */
    @PostMapping("/deploy")
    public CompletableFuture<ResponseEntity<Map<String, String>>> deploy() {
        return escrowService.deploy().thenApply(c -> {
            Map<String, String> response = new LinkedHashMap<>();
            response.put("contractAddress", c.getContractAddress());
            response.put("transactionHash", c.getTransactionReceipt()
                    .map(r -> r.getTransactionHash()).orElse(""));
            response.put("deployBlock", escrowService.getDeployBlock().toString());
            return ResponseEntity.ok(response);
        });
    }

    /**
     * 载入已部署的 Escrow(重启服务后用)
     */
    @PostMapping("/load")
    public ResponseEntity<Map<String, String>> load(@RequestParam String address,
                                                    @RequestParam BigInteger fromBlock) {
        escrowService.load(address, fromBlock);
        Map<String, String> response = new LinkedHashMap<>();
        response.put("contractAddress", address);
        return ResponseEntity.ok(response);
    }

    /**
     * 当前 Escrow 的地址与仲裁人
     */
    @GetMapping("/address")
    public CompletableFuture<ResponseEntity<Map<String, String>>> address() {
        return escrowService.arbiter().thenApply(arbiter -> {
            Map<String, String> response = new LinkedHashMap<>();
            response.put("contractAddress", escrowService.getContractAddress());
            response.put("arbiter", arbiter);
            return ResponseEntity.ok(response);
        });
    }

    /**
     * 从 OrderCreated 事件重建的订单列表
     */
    @GetMapping("/orders")
    public ResponseEntity<List<Map<String, String>>> orders() throws Exception {
        return ResponseEntity.ok(escrowService.listOrders());
    }

    /**
     * 单个订单的当前状态
     */
    @GetMapping("/orders/{id}")
    public CompletableFuture<ResponseEntity<Map<String, String>>> order(@PathVariable BigInteger id) {
        return escrowService.getOrder(id).thenApply(ResponseEntity::ok);
    }

    /**
     * 仲裁: sellerWins=true 判给卖家,false 退给买家
     */
    @PostMapping("/orders/{id}/resolve")
    public CompletableFuture<ResponseEntity<Map<String, String>>> resolve(@PathVariable BigInteger id,
                                                                        @RequestParam boolean sellerWins) {
        return escrowService.resolve(id, sellerWins).thenApply(receipt -> {
            Map<String, String> response = new LinkedHashMap<>();
            response.put("transactionHash", receipt.getTransactionHash());
            response.put("blockNumber", receipt.getBlockNumber().toString());
            response.put("status", "0x1".equals(receipt.getStatus()) ? "success" : "reverted");
            return ResponseEntity.ok(response);
        });
    }
}