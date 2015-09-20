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

package com.perl5.lang.perl.psi.impl;

import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.PsiReference;
import com.intellij.psi.impl.source.tree.PsiCommentImpl;
import com.intellij.psi.tree.IElementType;
import com.perl5.lang.perl.psi.PerlVisitor;
import com.perl5.lang.perl.psi.references.PerlHeredocReference;
import org.jetbrains.annotations.NotNull;

public class PerlHeredocTerminatorElementImpl extends PsiCommentImpl
{
	public PerlHeredocTerminatorElementImpl(IElementType type, CharSequence text)
	{
		super(type, text);
	}

	@Override
	public void accept(@NotNull PsiElementVisitor visitor)
	{
		if (visitor instanceof PerlVisitor) ((PerlVisitor) visitor).visitHeredocTeminator(this);
		else super.accept(visitor);
	}

	@NotNull
	@Override
	public PsiReference[] getReferences()
	{
		return new PsiReference[]{new PerlHeredocReference(this, new TextRange(0, getTextLength()))};
	}

//	@Override
//	public PsiElement setName(@NotNull String name) throws IncorrectOperationException
//	{
//	}
//
//	@Nullable
//	@Override
//	public PsiElement getNameIdentifier()
//	{
//		return getPsi();
//	}
//
//	// todo we should move this to some superclass
//	@Override
//	public String getName()
//	{
//		PsiElement nameIdentifier = getNameIdentifier();
//		return nameIdentifier == null ? null : nameIdentifier.getText();
//	}
//
//	@Override
//	public String getPresentableName()
//	{
//		return getName();
//	}
//

}
