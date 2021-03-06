/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.shardingsphere.core.rewrite.feature.encrypt.token.generator;

import lombok.Getter;
import lombok.Setter;
import org.apache.shardingsphere.core.preprocessor.statement.SQLStatementContext;
import org.apache.shardingsphere.core.rewrite.feature.encrypt.aware.EncryptRuleAware;
import org.apache.shardingsphere.core.rewrite.sql.token.generator.SQLTokenGenerator;
import org.apache.shardingsphere.core.rule.EncryptRule;

/**
 * Base SQL token generator for encrypt.
 *
 * @author zhangliang
 */
@Getter
@Setter
public abstract class BaseEncryptSQLTokenGenerator implements SQLTokenGenerator, EncryptRuleAware {
    
    private EncryptRule encryptRule;
    
    @Override
    public final boolean isGenerateSQLToken(final SQLStatementContext sqlStatementContext) {
        return isGenerateSQLTokenForEncrypt(sqlStatementContext) && isNeedEncrypt(sqlStatementContext);
    }
    
    protected abstract boolean isGenerateSQLTokenForEncrypt(SQLStatementContext sqlStatementContext);
    
    private boolean isNeedEncrypt(final SQLStatementContext sqlStatementContext) {
        for (String each : sqlStatementContext.getTablesContext().getTableNames()) {
            if (encryptRule.findEncryptTable(each).isPresent()) {
                return true;
            }
        }
        return false;
    }
}
