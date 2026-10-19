# 区块链大作业 · 路线 A：Escrow 托管交易平台后端

基于 **Spring Boot + Web3j** 的托管交易（Escrow）平台后端。平台方部署合约、充当仲裁人，并通过 REST 接口对外提供订单查询与仲裁服务。所有上链交易由服务器持有的仲裁人账户签名。

- 网络：POTOS 测试网
- RPC：`https://rpc-testnet.potos.hk`
- 区块浏览器：`https://scan-testnet.potos.hk`

## 功能

- 部署 / 载入 Escrow 合约
- 查询合约地址与仲裁人
- 从 `OrderCreated` 事件重建订单列表（POTOS 的 `eth_getLogs` 每次只扫描 10 个区块，代码中按 10 个区块分段查询）
- 查询单个订单的完整状态
- 由后端仲裁人账户调用 `resolve` 完成仲裁

## 环境要求

- JDK 21
- Gradle（仓库自带 wrapper，无需单独安装）
- 可访问 POTOS 测试网的网络

## 环境变量

| 变量名 | 必填 | 说明 |
| ------ | ---- | ---- |
| `WEB3J_PRIVATE_KEY` | 是 | 仲裁人（后端账户）私钥，用于部署合约和签名仲裁交易。只通过环境变量注入，禁止写入仓库中的任何文件。 |

可选覆盖项（Spring Boot 会自动读取同名环境变量）：

| 变量名 | 默认值 | 说明 |
| ------ | ------ | ---- |
| `WEB3J_CLIENT_ADDRESS` | `https://rpc-testnet.potos.hk` | JSON-RPC 地址 |
| `WEB3J_GAS_PRICE` | `1000000` | POTOS 测试网 gas price 约为 21000 wei，默认的 20 gwei 会多付约一百万倍 |
| `WEB3J_GAS_LIMIT` | `6721975` | 单笔交易 gas limit |
| `SERVER_PORT` | `8080` | HTTP 端口 |

启动前导出私钥（每开一个新终端都要重新导出）：

```bash
export WEB3J_PRIVATE_KEY=<仲裁人私钥>
```

> 私钥泄露等于把仲裁权交给别人，且服务器上每一笔仲裁交易的 gas 都由该账户支付。不要把私钥写进 `application.properties`、`.env`、脚本或 README。

## 运行

```bash
# 修改过 .sol 合约后需要重新生成 Java 封装
bash gradlew generateContractWrappers

# 启动服务
bash gradlew bootRun
```

服务启动后监听 `http://localhost:8080`，健康检查：`GET /actuator/health`。

## API

### Escrow

| 方法 | 路径 | 参数 | 返回 |
| ---- | ---- | ---- | ---- |
| POST | `/api/escrow/deploy` | 无 | `contractAddress`、`transactionHash`、`deployBlock` |
| POST | `/api/escrow/load` | `address`、`fromBlock` | `contractAddress` |
| GET | `/api/escrow/address` | 无 | `contractAddress`、`arbiter` |
| GET | `/api/escrow/orders/{id}` | 无 | `buyer`、`seller`、`token`、`amount`、`deadline`、`status`（文字）、`description` |
| GET | `/api/escrow/orders` | 无 | 数组，每项 `id`、`buyer`、`seller`、`amount`、`blockNumber`、`transactionHash` |
| POST | `/api/escrow/orders/{id}/resolve` | `sellerWins` | `transactionHash`、`blockNumber`、`status` |

订单状态取值：`Created`、`Paid`、`Completed`、`Refunded`、`Disputed`。

示例：

```bash
# 部署（仲裁人 = 后端账户）
curl -X POST http://localhost:8080/api/escrow/deploy

# 重启后载入已有合约，fromBlock 填部署区块
curl -X POST "http://localhost:8080/api/escrow/load?address=0x8ed3d4136eEf2Ad412C327737B61Da1Eea806ff8&fromBlock=2671871"

curl -X GET http://localhost:8080/api/escrow/address
curl -X GET http://localhost:8080/api/escrow/orders
curl -X GET http://localhost:8080/api/escrow/orders/0

# 仲裁：true 判给卖家，false 退给买家
curl -X POST "http://localhost:8080/api/escrow/orders/1/resolve?sellerWins=false"
```

### 原示例 SimpleStorage（保留）

| 方法 | 路径 |
| ---- | ---- |
| POST | `/api/storage/deploy` |
| POST | `/api/storage/load?address={contractAddress}` |
| GET | `/api/storage/value/get` |
| POST | `/api/storage/value/set?value={value}` |
| GET | `/api/storage/address` |

## 合约地址

| 合约 | 地址 |
| ---- | ---- |
| Escrow（路线 A 由后端部署） | `0x8ed3d4136eEf2Ad412C327737B61Da1Eea806ff8` |
| HMKToken | `0x0fea8e1adba2dbb5c1a4c2fdf821576541183411` |
| 仲裁人 / 后端账户 | `0x0Ac84dfd536DfA10Faf0cCd283595b54b756D38c` |

4.6 验收中的账户：

| 角色 | 地址 |
| ---- | ---- |
| 买家 A | `0x779ED69bf966eb5b9E5F16f18E9d1a34f314Be87` |
| 卖家 B | `0x1f5a8566880c0D242F145B9172812eC18F50D1Ea` |

订单 0：金额 30 HMK，状态 `Completed`；订单 1：金额 30 HMK，状态 `Refunded`。

## 交易记录

| 操作 | 交易哈希 | 区块浏览器链接 | 发起账户 |
| ---- | -------- | -------------- | -------- |
| 部署 Escrow | `0xaf5597ec83d46a3096c2b76fb1ea03b05b011fecd769a006fdfad6cf2091c83f` | https://scan-testnet.potos.hk/tx/0xaf5597ec83d46a3096c2b76fb1ea03b05b011fecd769a006fdfad6cf2091c83f | `0x0Ac84dfd536DfA10Faf0cCd283595b54b756D38c` |
| createOrder 订单 0 | `0x6371750c52b8fa0c0c0bb562c663aca36bb4d4904db40556374a3d94412dd1d6` | https://scan-testnet.potos.hk/tx/0x6371750c52b8fa0c0c0bb562c663aca36bb4d4904db40556374a3d94412dd1d6?tab=index | `0x779ED69bf966eb5b9E5F16f18E9d1a34f314Be87` |
| approve | `0x27948eedc9cd1cf2dcfbf032996ed57411d29fc2ecb2a386059c1e316778a7f4` | https://scan-testnet.potos.hk/tx/0x27948eedc9cd1cf2dcfbf032996ed57411d29fc2ecb2a386059c1e316778a7f4 | `0x779ED69bf966eb5b9E5F16f18E9d1a34f314Be87` |
| deposit 订单 0 | `0x506905a9eaa5c65ff7baf0f2536fa80ca90f6e31c96f5242b4ad2d07e467e957` | https://scan-testnet.potos.hk/tx/0x506905a9eaa5c65ff7baf0f2536fa80ca90f6e31c96f5242b4ad2d07e467e957 | `0x779ED69bf966eb5b9E5F16f18E9d1a34f314Be87` |
| dispute 订单 1 | `0xec5c601d85c0f802af297b2096950c83757aac16486476fb59cb62e73b73a0f9` | https://scan-testnet.potos.hk/tx/0xec5c601d85c0f802af297b2096950c83757aac16486476fb59cb62e73b73a0f9 | `0x1f5a8566880c0D242F145B9172812eC18F50D1Ea` |
| resolve 订单 1 | `0x3725e3348e6e441d7a74d7d19ae3acc15f757b7fb14bba29815ad2c5c0fcb496` | https://scan-testnet.potos.hk/tx/0x3725e3348e6e441d7a74d7d19ae3acc15f757b7fb14bba29815ad2c5c0fcb496?tab=index | `0x0Ac84dfd536DfA10Faf0cCd283595b54b756D38c` |
| confirm 订单 0 | `0xb73fbfe2b9279d3216b346b4c6823d84aafc0833c1a53056df55f567cf664a8a` | https://scan-testnet.potos.hk/tx/0xb73fbfe2b9279d3216b346b4c6823d84aafc0833c1a53056df55f567cf664a8a | `0x779ED69bf966eb5b9E5F16f18E9d1a34f314Be87` |
| resolve 失败（路线 A） | `0xcb8aab99738a729825dbd3512ceb794c0d4a9c3f44aa09c93cfc845bdacb2165` | https://scan-testnet.potos.hk/tx/0xcb8aab99738a729825dbd3512ceb794c0d4a9c3f44aa09c93cfc845bdacb2165 | `0x0Ac84dfd536DfA10Faf0cCd283595b54b756D38c` |

> 最后一笔 `resolve 失败` 已上链，回执 `status = 0x0`、`logs` 为空，gas 仍由后端账户支付。HTTP 层返回 500，是因为 Web3j 在收到 `status=0x0` 的回执后抛出 `TransactionException`，交易哈希在该异常信息中。

## 代码结构

```
src/main/java/com/wetech/demo/web3j/
├── Application.java
├── config/Web3jConfig.java              # Web3j、Credentials、GasProvider
├── contracts/                           # 由 generateContractWrappers 生成
│   ├── escrow/Escrow.java
│   ├── hmktoken/HMKToken.java
│   ├── ierc20/IERC20.java
│   └── simplestorage/SimpleStorage.java
├── controller/
│   ├── EscrowController.java
│   └── SimpleStorageController.java
└── service/
    ├── EscrowService.java
    └── SimpleStorageService.java

src/main/resources/
├── application.properties
└── contracts/                           # Escrow.sol、HMKToken.sol、SimpleStorage.sol
```

## 实现说明

- 订单列表不通过 `getOrder(0..orderCount-1)` 循环读取，而是扫描 `OrderCreated` 事件日志重建。这样只需按订单创建时返回一次列表，不需要为每笔订单各发一次 `eth_call`。
- POTOS 测试网的 `eth_getLogs` 单次最多扫描 `fromBlock` 起的 10 个区块，超出部分会被静默丢弃，因此 `EscrowService.listOrders()` 按 10 个区块一段分批查询后拼接。
- `resolve` 失败时交易依然会被打包上链（`status=0x0`），gas 照扣。后端把 Web3j 抛出的 `TransactionException` 原样暴露为 HTTP 500。
