/*
 * Copyright 2016 Alexandr Evstigneev
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

package com.perl5.lang.perl.parser.moose.stubs;

import com.intellij.lang.ASTNode;
import com.intellij.lang.Language;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.PsiElement;
import com.perl5.lang.perl.idea.stubs.subsdefinitions.PerlSubDefinitionStub;
import com.perl5.lang.perl.idea.stubs.subsdefinitions.PerlSubDefinitionStubElementType;
import com.perl5.lang.perl.lexer.PerlElementTypes;
import com.perl5.lang.perl.parser.elementTypes.PsiElementProvider;
import com.perl5.lang.perl.parser.moose.psi.PerlMooseOverrideStatement;
import com.perl5.lang.perl.parser.moose.psi.impl.PerlMooseOverrideStatementImpl;
import com.perl5.lang.perl.psi.PerlSubDefinitionBase;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by hurricup on 29.11.2015.
 */
public class PerlMooseOverrideStubElementType extends PerlSubDefinitionStubElementType implements PerlElementTypes, PsiElementProvider
{
	public PerlMooseOverrideStubElementType(String name)
	{
		super(name);
	}

	public PerlMooseOverrideStubElementType(@NotNull @NonNls String debugName, @Nullable Language language)
	{
		super(debugName, language);
	}

	@Override
	public PerlSubDefinitionBase<PerlSubDefinitionStub> createPsi(@NotNull PerlSubDefinitionStub stub)
	{
		return new PerlMooseOverrideStatementImpl(stub, this);
	}

	@Override
	public boolean shouldCreateStub(ASTNode node)
	{
		PsiElement psi = node.getPsi();

		return psi instanceof PerlMooseOverrideStatement &&
				psi.isValid() &&
				StringUtil.isNotEmpty(((PerlMooseOverrideStatement) psi).getSubName());
	}

	@NotNull
	@Override
	public PsiElement getPsiElement(@NotNull ASTNode node)
	{
		return new PerlMooseOverrideStatementImpl(node);
	}
}
