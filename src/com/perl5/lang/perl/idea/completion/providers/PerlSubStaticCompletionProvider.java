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

package com.perl5.lang.perl.idea.completion.providers;

import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionProvider;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.util.ProcessingContext;
import com.perl5.lang.perl.idea.completion.util.PerlSubCompletionUtil;
import com.perl5.lang.perl.psi.*;
import com.perl5.lang.perl.util.PerlGlobUtil;
import com.perl5.lang.perl.util.PerlSubUtil;
import org.jetbrains.annotations.NotNull;

/**
 * Created by hurricup on 01.06.2015.
 */
public class PerlSubStaticCompletionProvider extends CompletionProvider<CompletionParameters>
{
	public void addCompletions(@NotNull CompletionParameters parameters,
							   ProcessingContext context,
							   @NotNull CompletionResultSet resultSet)
	{
		PsiElement method = parameters.getPosition().getParent();
		assert method instanceof PsiPerlMethod;

		String packageName = ((PsiPerlMethod) method).getPackageName();

		Project project = parameters.getPosition().getProject();

		// defined subs
		for (PerlSubDefinitionBase subDefinition : PerlSubUtil.getSubDefinitions(project, "*" + packageName))
		{
			if (subDefinition.isStatic())
			{
				resultSet.addElement(PerlSubCompletionUtil.getSubDefinitionLookupElement(subDefinition));
			}
		}

		// declared subs
		for (PerlSubDeclaration subDeclaration : PerlSubUtil.getSubDeclarations(project, "*" + packageName))
		{
			if (subDeclaration.isStatic())
			{
				resultSet.addElement(PerlSubCompletionUtil.getSubDeclarationLookupElement(subDeclaration));
			}
		}

		// Globs
		for (PerlGlobVariable globVariable : PerlGlobUtil.getGlobsDefinitions(project, "*" + packageName))
		{
			if (globVariable.getName() != null)
			{
				resultSet.addElement(PerlSubCompletionUtil.getGlobLookupElement(globVariable));
			}
		}

		// Constants
		for (PerlConstant stringConstant : PerlSubUtil.getConstantsDefinitions(project, "*" + packageName))
		{
			if (stringConstant.getName() != null)
			{
				resultSet.addElement(PerlSubCompletionUtil.getConstantLookupElement(stringConstant));
			}
		}


	}
}
