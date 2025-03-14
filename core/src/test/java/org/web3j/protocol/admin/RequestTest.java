/*
 * Copyright 2019 Web3 Labs Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package org.web3j.protocol.admin;

import java.math.BigInteger;

import org.junit.jupiter.api.Test;

import org.web3j.protocol.RequestTester;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.http.HttpService;

class RequestTest extends RequestTester {

    private Admin web3j;

    @Override
    protected void initWeb3Client(HttpService httpService) {
        web3j = Admin.build(httpService);
    }

    @Test
    void testPersonalListAccounts() throws Exception {
        web3j.personalListAccounts().send();

        verifyResult(
                "{\"jsonrpc\":\"2.0\",\"method\":\"personal_listAccounts\","
                        + "\"params\":[],\"id\":1}");
    }

    @Test
    void testPersonalNewAccount() throws Exception {
        web3j.personalNewAccount("password").send();

        verifyResult(
                "{\"jsonrpc\":\"2.0\",\"method\":\"personal_newAccount\","
                        + "\"params\":[\"password\"],\"id\":1}");
    }

    @Test
    void testPersonalSendTransaction() throws Exception {
        web3j.personalSendTransaction(
                        new Transaction(
                                "FROM",
                                BigInteger.ONE,
                                BigInteger.TEN,
                                BigInteger.ONE,
                                "TO",
                                BigInteger.ZERO,
                                "DATA",
                                61L,
                                BigInteger.ZERO,
                                BigInteger.TEN),
                        "password")
                .send();
        verifyResult(
                "{\"jsonrpc\":\"2.0\",\"method\":\"personal_sendTransaction\",\"params\":[{\"from\":\"FROM\",\"to\":\"TO\",\"gas\":\"0x1\",\"gasPrice\":\"0xa\",\"value\":\"0x0\",\"data\":\"0xDATA\",\"nonce\":\"0x1\",\"chainId\":\"0x3d\",\"maxPriorityFeePerGas\":\"0x0\",\"maxFeePerGas\":\"0xa\"},\"password\"],\"id\":1}");
    }

    @Test
    void testPersonalUnlockAccount() throws Exception {
        web3j.personalUnlockAccount(
                        "0xfc390d8a8ddb591b010fda52f4db4945742c3809", "hunter2", BigInteger.ONE)
                .send();

        verifyResult(
                "{\"jsonrpc\":\"2.0\",\"method\":\"personal_unlockAccount\","
                        + "\"params\":[\"0xfc390d8a8ddb591b010fda52f4db4945742c3809\",\"hunter2\",1],"
                        + "\"id\":1}");
    }

    @Test
    void testPersonalUnlockAccountNoDuration() throws Exception {
        web3j.personalUnlockAccount("0xfc390d8a8ddb591b010fda52f4db4945742c3809", "hunter2").send();

        verifyResult(
                "{\"jsonrpc\":\"2.0\",\"method\":\"personal_unlockAccount\","
                        + "\"params\":[\"0xfc390d8a8ddb591b010fda52f4db4945742c3809\",\"hunter2\",null],"
                        + "\"id\":1}");
    }

    @Test
    void testTxPoolContent() throws Exception {
        web3j.txPoolContent().send();

        verifyResult(
                "{\"jsonrpc\":\"2.0\",\"method\":\"txpool_content\"," + "\"params\":[],\"id\":1}");
    }
}
