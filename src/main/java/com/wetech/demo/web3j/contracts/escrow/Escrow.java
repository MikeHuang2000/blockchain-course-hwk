package com.wetech.demo.web3j.contracts.escrow;

import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.DynamicStruct;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint8;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/LFDT-web3j/web3j/tree/main/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 4.14.0.
 */
@SuppressWarnings("rawtypes")
public class Escrow extends Contract {
    public static final String BINARY = "6080604052348015600e575f5ffd5b506040516113e53803806113e5833981016040819052602b9160b3565b6001600160a01b03811660905760405162461bcd60e51b815260206004820152602360248201527f457363726f773a206172626974657220697320746865207a65726f206164647260448201526265737360e81b606482015260840160405180910390fd5b5f80546001600160a01b0319166001600160a01b039290921691909117905560de565b5f6020828403121560c2575f5ffd5b81516001600160a01b038116811460d7575f5ffd5b9392505050565b6112fa806100eb5f395ff3fe608060405234801561000f575f5ffd5b5060043610610090575f3560e01c806386d6282c1161006357806386d6282c146100eb578063b6b55f25146100fe578063ba0179b514610111578063d09ef24114610124578063fe25e00a14610144575f5ffd5b806302bd3421146100945780632453ffa8146100ba578063278ecde1146100c357806352a34b05146100d8575b5f5ffd5b6100a76100a2366004610e76565b61016e565b6040519081526020015b60405180910390f35b6100a760015481565b6100d66100d1366004610f5c565b61043d565b005b6100d66100e6366004610f83565b6105fc565b6100d66100f9366004610f5c565b610853565b6100d661010c366004610f5c565b61097b565b6100d661011f366004610f5c565b610b24565b610137610132366004610f5c565b610c90565b6040516100b19190611013565b5f54610156906001600160a01b031681565b6040516001600160a01b0390911681526020016100b1565b5f6001600160a01b0386166101d55760405162461bcd60e51b815260206004820152602260248201527f457363726f773a2073656c6c657220697320746865207a65726f206164647265604482015261737360f01b60648201526084015b60405180910390fd5b336001600160a01b038716036102385760405162461bcd60e51b815260206004820152602260248201527f457363726f773a2073656c6c65722063616e6e6f74206265207468652062757960448201526132b960f11b60648201526084016101cc565b5f84116102985760405162461bcd60e51b815260206004820152602860248201527f457363726f773a20616d6f756e74206d7573742062652067726561746572207460448201526768616e207a65726f60c01b60648201526084016101cc565b4283116102e75760405162461bcd60e51b815260206004820152601f60248201527f457363726f773a20646561646c696e6520697320696e2074686520706173740060448201526064016101cc565b600180546040805160e0810182523381526001600160a01b038a811660208084019182528b8316848601908152606085018c8152608086018c81525f60a0880181815260c089018e90528a8252600295869052989020875181549088166001600160a01b03199182161782559551818c018054918916918816919091179055925193830180549490961693909416929092179093555160038301555160048083019190915592516005820180549596939592949193909260ff19169184908111156103b4576103b4610fb1565b021790555060c082015160068201906103cd9082611122565b506103dd915082905060016111e1565b600155604080516001600160a01b0388811682526020820188905291810186905290881690339083907fa4be1e061958d160de04db54668a64922c36d2765b16644ae91acf127708970e9060600160405180910390a49695505050505050565b5f8181526002602052604090205481906001600160a01b031633146104745760405162461bcd60e51b81526004016101cc90611206565b816001805f8381526002602052604090206005015460ff16600481111561049d5761049d610fb1565b146104ba5760405162461bcd60e51b81526004016101cc9061123d565b5f8481526002602052604090206004810154421161051a5760405162461bcd60e51b815260206004820152601f60248201527f457363726f773a20646561646c696e6520686173206e6f74207061737365640060448201526064016101cc565b60058101805460ff19166003908117909155600282015482549183015460405163a9059cbb60e01b81526001600160a01b03938416600482015260248101919091525f929091169063a9059cbb906044016020604051808303815f875af1158015610587573d5f5f3e3d5ffd5b505050506040513d601f19601f820116820180604052508101906105ab919061126b565b9050806105ca5760405162461bcd60e51b81526004016101cc9061128d565b60405186907f303a31648d19f17852cf82657162c4bdd4a06ab3f36f1e7ae1118f4ca6473e11905f90a2505050505050565b5f546001600160a01b0316331461065f5760405162461bcd60e51b815260206004820152602160248201527f457363726f773a2063616c6c6572206973206e6f7420746865206172626974656044820152603960f91b60648201526084016101cc565b816004805f8381526002602052604090206005015460ff16600481111561068857610688610fb1565b146106a55760405162461bcd60e51b81526004016101cc9061123d565b5f848152600260205260409020836106be5760036106c1565b60025b60058201805460ff191660018360048111156106df576106df610fb1565b02179055505f84156107725760028201546001830154600384015460405163a9059cbb60e01b81526001600160a01b039283166004820152602481019190915291169063a9059cbb906044016020604051808303815f875af1158015610747573d5f5f3e3d5ffd5b505050506040513d601f19601f8201168201806040525081019061076b919061126b565b90506107f2565b60028201548254600384015460405163a9059cbb60e01b81526001600160a01b039283166004820152602481019190915291169063a9059cbb906044016020604051808303815f875af11580156107cb573d5f5f3e3d5ffd5b505050506040513d601f19601f820116820180604052508101906107ef919061126b565b90505b8061080f5760405162461bcd60e51b81526004016101cc9061128d565b857f649ff37700d4eb8a5bc86ef06a55183597fa5c3c6b53ec4b9ee510cb2089ee2686604051610843911515815260200190565b60405180910390a2505050505050565b806001805f8381526002602052604090206005015460ff16600481111561087c5761087c610fb1565b146108995760405162461bcd60e51b81526004016101cc9061123d565b5f83815260026020526040902080546001600160a01b03163314806108ca575060018101546001600160a01b031633145b6109315760405162461bcd60e51b815260206004820152603260248201527f457363726f773a2063616c6c6572206973206e656974686572207468652062756044820152713cb2b9103737b9103a34329039b2b63632b960711b60648201526084016101cc565b60058101805460ff1916600417905560405133815284907fde481be1936299e9233634e9f3cd92028f797634de15defa790fb2d7a013c95a9060200160405180910390a250505050565b5f8181526002602052604090205481906001600160a01b031633146109b25760405162461bcd60e51b81526004016101cc90611206565b815f805f8381526002602052604090206005015460ff1660048111156109da576109da610fb1565b146109f75760405162461bcd60e51b81526004016101cc9061123d565b5f84815260026020819052604080832060058101805460ff19166001179055918201548254600384015492516323b872dd60e01b81526001600160a01b039182166004820152306024820152604481019390935292939216906323b872dd906064016020604051808303815f875af1158015610a75573d5f5f3e3d5ffd5b505050506040513d601f19601f82011682018060405250810190610a99919061126b565b905080610af25760405162461bcd60e51b815260206004820152602160248201527f457363726f773a20746f6b656e207472616e7366657246726f6d206661696c656044820152601960fa1b60648201526084016101cc565b60405186907f15e33efda35e5da2fb096ba49585307ba468af144aea691da964aaff1a7f3a57905f90a2505050505050565b5f8181526002602052604090205481906001600160a01b03163314610b5b5760405162461bcd60e51b81526004016101cc90611206565b816001805f8381526002602052604090206005015460ff166004811115610b8457610b84610fb1565b14610ba15760405162461bcd60e51b81526004016101cc9061123d565b5f84815260026020819052604080832060058101805460ff1916841790559182015460018301546003840154925163a9059cbb60e01b81526001600160a01b0391821660048201526024810193909352929392169063a9059cbb906044016020604051808303815f875af1158015610c1b573d5f5f3e3d5ffd5b505050506040513d601f19601f82011682018060405250810190610c3f919061126b565b905080610c5e5760405162461bcd60e51b81526004016101cc9061128d565b60405186907f02a7d4d9472af7118644884b9a0ee443540c0027e938dd0aa35be8ecbe946c0a905f90a2505050505050565b610ccf6040805160e0810182525f8082526020820181905291810182905260608101829052608081018290529060a08201908152602001606081525090565b6001548210610d205760405162461bcd60e51b815260206004820152601c60248201527f457363726f773a206f7264657220646f6573206e6f742065786973740000000060448201526064016101cc565b5f82815260026020818152604092839020835160e08101855281546001600160a01b03908116825260018301548116938201939093529281015490911692820192909252600382015460608201526004808301546080830152600583015491929160a084019160ff90911690811115610d9b57610d9b610fb1565b6004811115610dac57610dac610fb1565b8152602001600682018054610dc090611093565b80601f0160208091040260200160405190810160405280929190818152602001828054610dec90611093565b8015610e375780601f10610e0e57610100808354040283529160200191610e37565b820191905f5260205f20905b815481529060010190602001808311610e1a57829003601f168201915b5050505050815250509050919050565b80356001600160a01b0381168114610e5d575f5ffd5b919050565b634e487b7160e01b5f52604160045260245ffd5b5f5f5f5f5f60a08688031215610e8a575f5ffd5b610e9386610e47565b9450610ea160208701610e47565b93506040860135925060608601359150608086013567ffffffffffffffff811115610eca575f5ffd5b8601601f81018813610eda575f5ffd5b803567ffffffffffffffff811115610ef457610ef4610e62565b604051601f8201601f19908116603f0116810167ffffffffffffffff81118282101715610f2357610f23610e62565b6040528181528282016020018a1015610f3a575f5ffd5b816020840160208301375f602083830101528093505050509295509295909350565b5f60208284031215610f6c575f5ffd5b5035919050565b8015158114610f80575f5ffd5b50565b5f5f60408385031215610f94575f5ffd5b823591506020830135610fa681610f73565b809150509250929050565b634e487b7160e01b5f52602160045260245ffd5b60058110610fe157634e487b7160e01b5f52602160045260245ffd5b9052565b5f81518084528060208401602086015e5f602082860101526020601f19601f83011685010191505092915050565b6020815260018060a01b03825116602082015260018060a01b03602083015116604082015260018060a01b03604083015116606082015260608201516080820152608082015160a08201525f60a083015161107160c0840182610fc5565b5060c083015160e08084015261108b610100840182610fe5565b949350505050565b600181811c908216806110a757607f821691505b6020821081036110c557634e487b7160e01b5f52602260045260245ffd5b50919050565b601f82111561111d578282111561111d57805f5260205f20601f840160051c60208510156110f657505f5b90810190601f840160051c035f5b81811015611119575f83820155600101611104565b5050505b505050565b815167ffffffffffffffff81111561113c5761113c610e62565b6111508161114a8454611093565b846110cb565b6020601f821160018114611182575f831561116b5750848201515b5f19600385901b1c1916600184901b1784556111da565b5f84815260208120601f198516915b828110156111b15787850151825560209485019460019092019101611191565b50848210156111ce57868401515f19600387901b60f8161c191681555b505060018360011b0184555b5050505050565b8082018082111561120057634e487b7160e01b5f52601160045260245ffd5b92915050565b6020808252601f908201527f457363726f773a2063616c6c6572206973206e6f742074686520627579657200604082015260600190565b602080825260149082015273457363726f773a2077726f6e672073746174757360601b604082015260600190565b5f6020828403121561127b575f5ffd5b815161128681610f73565b9392505050565b6020808252601d908201527f457363726f773a20746f6b656e207472616e73666572206661696c656400000060408201526060019056fea2646970667358221220e7ab7d77eecf4ed30611869accbd0b5ca8d84f5d880936146c9c596e368437de64736f6c63430008230033";

    private static String librariesLinkedBinary;

    public static final String FUNC_ARBITER = "arbiter";

    public static final String FUNC_CONFIRM = "confirm";

    public static final String FUNC_CREATEORDER = "createOrder";

    public static final String FUNC_DEPOSIT = "deposit";

    public static final String FUNC_DISPUTE = "dispute";

    public static final String FUNC_GETORDER = "getOrder";

    public static final String FUNC_ORDERCOUNT = "orderCount";

    public static final String FUNC_REFUND = "refund";

    public static final String FUNC_RESOLVE = "resolve";

    public static final Event ORDERCOMPLETED_EVENT = new Event("OrderCompleted", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>(true) {}));
    ;

    public static final Event ORDERCREATED_EVENT = new Event("OrderCreated", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event ORDERDISPUTED_EVENT = new Event("OrderDisputed", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>(true) {}, new TypeReference<Address>() {}));
    ;

    public static final Event ORDERPAID_EVENT = new Event("OrderPaid", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>(true) {}));
    ;

    public static final Event ORDERREFUNDED_EVENT = new Event("OrderRefunded", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>(true) {}));
    ;

    public static final Event ORDERRESOLVED_EVENT = new Event("OrderResolved", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>(true) {}, new TypeReference<Bool>() {}));
    ;

    @Deprecated
    protected Escrow(String contractAddress, Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Escrow(String contractAddress, Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected Escrow(String contractAddress, Web3j web3j, TransactionManager transactionManager,
            BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected Escrow(String contractAddress, Web3j web3j, TransactionManager transactionManager,
            ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static List<OrderCompletedEventResponse> getOrderCompletedEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(ORDERCOMPLETED_EVENT, transactionReceipt);
        ArrayList<OrderCompletedEventResponse> responses = new ArrayList<OrderCompletedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            OrderCompletedEventResponse typedResponse = new OrderCompletedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.id = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static OrderCompletedEventResponse getOrderCompletedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(ORDERCOMPLETED_EVENT, log);
        OrderCompletedEventResponse typedResponse = new OrderCompletedEventResponse();
        typedResponse.log = log;
        typedResponse.id = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<OrderCompletedEventResponse> orderCompletedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getOrderCompletedEventFromLog(log));
    }

    public Flowable<OrderCompletedEventResponse> orderCompletedEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ORDERCOMPLETED_EVENT));
        return orderCompletedEventFlowable(filter);
    }

    public static List<OrderCreatedEventResponse> getOrderCreatedEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(ORDERCREATED_EVENT, transactionReceipt);
        ArrayList<OrderCreatedEventResponse> responses = new ArrayList<OrderCreatedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            OrderCreatedEventResponse typedResponse = new OrderCreatedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.id = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.buyer = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.seller = (String) eventValues.getIndexedValues().get(2).getValue();
            typedResponse.token = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.deadline = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static OrderCreatedEventResponse getOrderCreatedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(ORDERCREATED_EVENT, log);
        OrderCreatedEventResponse typedResponse = new OrderCreatedEventResponse();
        typedResponse.log = log;
        typedResponse.id = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.buyer = (String) eventValues.getIndexedValues().get(1).getValue();
        typedResponse.seller = (String) eventValues.getIndexedValues().get(2).getValue();
        typedResponse.token = (String) eventValues.getNonIndexedValues().get(0).getValue();
        typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
        typedResponse.deadline = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
        return typedResponse;
    }

    public Flowable<OrderCreatedEventResponse> orderCreatedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getOrderCreatedEventFromLog(log));
    }

    public Flowable<OrderCreatedEventResponse> orderCreatedEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ORDERCREATED_EVENT));
        return orderCreatedEventFlowable(filter);
    }

    public static List<OrderDisputedEventResponse> getOrderDisputedEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(ORDERDISPUTED_EVENT, transactionReceipt);
        ArrayList<OrderDisputedEventResponse> responses = new ArrayList<OrderDisputedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            OrderDisputedEventResponse typedResponse = new OrderDisputedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.id = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.by = (String) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static OrderDisputedEventResponse getOrderDisputedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(ORDERDISPUTED_EVENT, log);
        OrderDisputedEventResponse typedResponse = new OrderDisputedEventResponse();
        typedResponse.log = log;
        typedResponse.id = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.by = (String) eventValues.getNonIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<OrderDisputedEventResponse> orderDisputedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getOrderDisputedEventFromLog(log));
    }

    public Flowable<OrderDisputedEventResponse> orderDisputedEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ORDERDISPUTED_EVENT));
        return orderDisputedEventFlowable(filter);
    }

    public static List<OrderPaidEventResponse> getOrderPaidEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(ORDERPAID_EVENT, transactionReceipt);
        ArrayList<OrderPaidEventResponse> responses = new ArrayList<OrderPaidEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            OrderPaidEventResponse typedResponse = new OrderPaidEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.id = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static OrderPaidEventResponse getOrderPaidEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(ORDERPAID_EVENT, log);
        OrderPaidEventResponse typedResponse = new OrderPaidEventResponse();
        typedResponse.log = log;
        typedResponse.id = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<OrderPaidEventResponse> orderPaidEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getOrderPaidEventFromLog(log));
    }

    public Flowable<OrderPaidEventResponse> orderPaidEventFlowable(DefaultBlockParameter startBlock,
            DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ORDERPAID_EVENT));
        return orderPaidEventFlowable(filter);
    }

    public static List<OrderRefundedEventResponse> getOrderRefundedEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(ORDERREFUNDED_EVENT, transactionReceipt);
        ArrayList<OrderRefundedEventResponse> responses = new ArrayList<OrderRefundedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            OrderRefundedEventResponse typedResponse = new OrderRefundedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.id = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static OrderRefundedEventResponse getOrderRefundedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(ORDERREFUNDED_EVENT, log);
        OrderRefundedEventResponse typedResponse = new OrderRefundedEventResponse();
        typedResponse.log = log;
        typedResponse.id = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<OrderRefundedEventResponse> orderRefundedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getOrderRefundedEventFromLog(log));
    }

    public Flowable<OrderRefundedEventResponse> orderRefundedEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ORDERREFUNDED_EVENT));
        return orderRefundedEventFlowable(filter);
    }

    public static List<OrderResolvedEventResponse> getOrderResolvedEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(ORDERRESOLVED_EVENT, transactionReceipt);
        ArrayList<OrderResolvedEventResponse> responses = new ArrayList<OrderResolvedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            OrderResolvedEventResponse typedResponse = new OrderResolvedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.id = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.sellerWins = (Boolean) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static OrderResolvedEventResponse getOrderResolvedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(ORDERRESOLVED_EVENT, log);
        OrderResolvedEventResponse typedResponse = new OrderResolvedEventResponse();
        typedResponse.log = log;
        typedResponse.id = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.sellerWins = (Boolean) eventValues.getNonIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<OrderResolvedEventResponse> orderResolvedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getOrderResolvedEventFromLog(log));
    }

    public Flowable<OrderResolvedEventResponse> orderResolvedEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ORDERRESOLVED_EVENT));
        return orderResolvedEventFlowable(filter);
    }

    public RemoteFunctionCall<String> arbiter() {
        final Function function = new Function(FUNC_ARBITER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> confirm(BigInteger id) {
        final Function function = new Function(
                FUNC_CONFIRM, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(id)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> createOrder(String seller, String token,
            BigInteger amount, BigInteger deadline, String description) {
        final Function function = new Function(
                FUNC_CREATEORDER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, seller), 
                new org.web3j.abi.datatypes.Address(160, token), 
                new org.web3j.abi.datatypes.generated.Uint256(amount), 
                new org.web3j.abi.datatypes.generated.Uint256(deadline), 
                new org.web3j.abi.datatypes.Utf8String(description)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> deposit(BigInteger id) {
        final Function function = new Function(
                FUNC_DEPOSIT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(id)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> dispute(BigInteger id) {
        final Function function = new Function(
                FUNC_DISPUTE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(id)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Order> getOrder(BigInteger id) {
        final Function function = new Function(FUNC_GETORDER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(id)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Order>() {}));
        return executeRemoteCallSingleValueReturn(function, Order.class);
    }

    public RemoteFunctionCall<BigInteger> orderCount() {
        final Function function = new Function(FUNC_ORDERCOUNT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> refund(BigInteger id) {
        final Function function = new Function(
                FUNC_REFUND, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(id)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> resolve(BigInteger id, Boolean sellerWins) {
        final Function function = new Function(
                FUNC_RESOLVE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(id), 
                new org.web3j.abi.datatypes.Bool(sellerWins)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static Escrow load(String contractAddress, Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit) {
        return new Escrow(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static Escrow load(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Escrow(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static Escrow load(String contractAddress, Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        return new Escrow(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static Escrow load(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new Escrow(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<Escrow> deploy(Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider, String _arbiter) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _arbiter)));
        return deployRemoteCall(Escrow.class, web3j, credentials, contractGasProvider, getDeploymentBinary(), encodedConstructor);
    }

    public static RemoteCall<Escrow> deploy(Web3j web3j, TransactionManager transactionManager,
            ContractGasProvider contractGasProvider, String _arbiter) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _arbiter)));
        return deployRemoteCall(Escrow.class, web3j, transactionManager, contractGasProvider, getDeploymentBinary(), encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<Escrow> deploy(Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit, String _arbiter) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _arbiter)));
        return deployRemoteCall(Escrow.class, web3j, credentials, gasPrice, gasLimit, getDeploymentBinary(), encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<Escrow> deploy(Web3j web3j, TransactionManager transactionManager,
            BigInteger gasPrice, BigInteger gasLimit, String _arbiter) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _arbiter)));
        return deployRemoteCall(Escrow.class, web3j, transactionManager, gasPrice, gasLimit, getDeploymentBinary(), encodedConstructor);
    }

    public static void linkLibraries(List<Contract.LinkReference> references) {
        librariesLinkedBinary = linkBinaryWithReferences(BINARY, references);
    }

    private static String getDeploymentBinary() {
        if (librariesLinkedBinary != null) {
            return librariesLinkedBinary;
        } else {
            return BINARY;
        }
    }

    public static class Order extends DynamicStruct {
        public String buyer;

        public String seller;

        public String token;

        public BigInteger amount;

        public BigInteger deadline;

        public BigInteger status;

        public String description;

        public Order(String buyer, String seller, String token, BigInteger amount,
                BigInteger deadline, BigInteger status, String description) {
            super(new org.web3j.abi.datatypes.Address(160, buyer), 
                    new org.web3j.abi.datatypes.Address(160, seller), 
                    new org.web3j.abi.datatypes.Address(160, token), 
                    new org.web3j.abi.datatypes.generated.Uint256(amount), 
                    new org.web3j.abi.datatypes.generated.Uint256(deadline), 
                    new org.web3j.abi.datatypes.generated.Uint8(status), 
                    new org.web3j.abi.datatypes.Utf8String(description));
            this.buyer = buyer;
            this.seller = seller;
            this.token = token;
            this.amount = amount;
            this.deadline = deadline;
            this.status = status;
            this.description = description;
        }

        public Order(Address buyer, Address seller, Address token, Uint256 amount, Uint256 deadline,
                Uint8 status, Utf8String description) {
            super(buyer, seller, token, amount, deadline, status, description);
            this.buyer = buyer.getValue();
            this.seller = seller.getValue();
            this.token = token.getValue();
            this.amount = amount.getValue();
            this.deadline = deadline.getValue();
            this.status = status.getValue();
            this.description = description.getValue();
        }
    }

    public static class OrderCompletedEventResponse extends BaseEventResponse {
        public BigInteger id;
    }

    public static class OrderCreatedEventResponse extends BaseEventResponse {
        public BigInteger id;

        public String buyer;

        public String seller;

        public String token;

        public BigInteger amount;

        public BigInteger deadline;
    }

    public static class OrderDisputedEventResponse extends BaseEventResponse {
        public BigInteger id;

        public String by;
    }

    public static class OrderPaidEventResponse extends BaseEventResponse {
        public BigInteger id;
    }

    public static class OrderRefundedEventResponse extends BaseEventResponse {
        public BigInteger id;
    }

    public static class OrderResolvedEventResponse extends BaseEventResponse {
        public BigInteger id;

        public Boolean sellerWins;
    }
}
