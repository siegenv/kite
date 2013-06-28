/*
 * Copyright 2013 Cloudera Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.cloudera.cdk.morphline.stdlib;

import java.util.ListIterator;

import com.cloudera.cdk.morphline.api.Command;
import com.cloudera.cdk.morphline.api.MorphlineContext;
import com.cloudera.cdk.morphline.api.Record;
import com.cloudera.cdk.morphline.base.AbstractCommand;
import com.typesafe.config.Config;

/**
 * Base class for convenient implementation of commands that do in-place updates on a field.
 */
public abstract class AbstractFieldTransformCommand extends AbstractCommand {

  private final String fieldName;
  
  public AbstractFieldTransformCommand(Config config, Command parent, Command child, MorphlineContext context) {
    super(config, parent, child, context);      
    this.fieldName = getConfigs().getString(config, "field");
  }
      
  @Override
  protected final boolean doProcess(Record record) {
    ListIterator iter = record.get(fieldName).listIterator();
    while (iter.hasNext()) {
      iter.set(transformFieldValue(iter.next()));
    }
    return super.doProcess(record);
  }
  
  /** Transforms the given input value to some output value */
  protected abstract Object transformFieldValue(Object value);
  
}
