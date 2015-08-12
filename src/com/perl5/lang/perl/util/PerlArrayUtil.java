/*
 * Copyright 2015 Alexandr Evstigneev
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

package com.perl5.lang.perl.util;

import com.intellij.openapi.project.Project;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.stubs.StubIndex;
import com.perl5.lang.perl.idea.stubs.variables.PerlVariableStubIndexKeys;
import com.perl5.lang.perl.lexer.PerlElementTypes;
import com.perl5.lang.perl.psi.PerlVariable;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

/**
 * Created by hurricup on 19.04.2015.
 */
public class PerlArrayUtil implements PerlElementTypes
{
	public static final HashSet<String> BUILT_IN = new HashSet<String>(Arrays.asList(
			"+",
			"-",
			"_",
			"F",
			"ARG",
			"LAST_MATCH_END",
			"ARGV",
			"INC",
			"LAST_MATCH_START"
	));

	public static boolean isBuiltIn(String variable)
	{
		return BUILT_IN.contains(variable);
	}


	/**
	 * Searching project files for global array definitions by specific package and variable name
	 *
	 * @param project       project to search in
	 * @param canonicalName canonical variable name package::name
	 * @return Collection of found definitions
	 */
	public static Collection<PerlVariable> getGlobalArrayDefinitions(Project project, String canonicalName)
	{
		assert canonicalName != null;
		return StubIndex.getElements(PerlVariableStubIndexKeys.KEY_ARRAY, canonicalName, project, GlobalSearchScope.allScope(project), PerlVariable.class);
	}

	/**
	 * Returns list of defined global arrays
	 *
	 * @param project project to search in
	 * @return collection of variable canonical names
	 */
	public static Collection<String> getDefinedGlobalArrayNames(Project project)
	{
		return StubIndex.getInstance().getAllKeys(PerlVariableStubIndexKeys.KEY_ARRAY, project);
	}

}
