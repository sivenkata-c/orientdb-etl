/*
 *
 *  * Copyright 2010-2014 Orient Technologies LTD (info(at)orientechnologies.com)
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *      http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package com.orientechnologies.orient.etl.transform;

import com.orientechnologies.orient.core.command.OCommandContext;
import com.orientechnologies.orient.core.command.script.OCommandExecutorScript;
import com.orientechnologies.orient.core.command.script.OCommandScript;
import com.orientechnologies.orient.core.record.impl.ODocument;

import java.util.HashMap;
import java.util.Map;

public class OCodeTransformer extends OAbstractTransformer {
  protected String                 language = "javascript";
  protected String                 code;
  protected OCommandExecutorScript cmd;
  protected Map<Object, Object>    params   = new HashMap<Object, Object>();

  @Override
  public void configure(final ODocument iConfiguration) {
    if (iConfiguration.containsField("language"))
      language = iConfiguration.field("language");
    code = iConfiguration.field("code");
  }

  @Override
  public String getName() {
    return "code";
  }

  @Override
  public Object transform(final Object input, final OCommandContext iContext) {
    if (input == null)
      return null;

    if (cmd == null)
      cmd = new OCommandExecutorScript().parse(new OCommandScript(language, code));

    params.put("record", input);
    return cmd.executeInContext(iContext, params);
  }
}