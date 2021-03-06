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

package com.perl5.lang.perl.idea.structureView;

import com.intellij.ide.structureView.StructureViewBuilder;
import com.intellij.ide.structureView.StructureViewModel;
import com.intellij.ide.structureView.TreeBasedStructureViewBuilder;
import com.intellij.lang.PsiStructureViewFactory;
import com.intellij.openapi.editor.Editor;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.perl5.lang.perl.PerlLanguage;
import com.perl5.lang.perl.lexer.PerlElementTypes;
import com.perl5.lang.pod.PodLanguage;
import com.perl5.lang.pod.idea.structureView.PodStructureViewModel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by hurricup on 15.08.2015.
 */
public class PerlStructureViewFactory implements PsiStructureViewFactory
{
	@Nullable
	@Override
	public StructureViewBuilder getStructureViewBuilder(final PsiFile psiFile)
	{
		return new TreeBasedStructureViewBuilder()
		{
			@NotNull
			@Override
			public StructureViewModel createStructureViewModel(@Nullable Editor editor)
			{
				if (editor != null)
				{
					int offset = editor.getCaretModel().getOffset();
					PsiElement element = psiFile.getViewProvider().findElementAt(offset, PerlLanguage.INSTANCE);
					if (element != null && element.getNode().getElementType() == PerlElementTypes.POD)
					{
						PsiFile podTree = psiFile.getViewProvider().getPsi(PodLanguage.INSTANCE);
						if (podTree != null)
						{
							return new PodStructureViewModel(podTree, editor);
						}
					}
				}

				return new PerlStructureViewModel(psiFile, editor);
			}

			@Override
			public boolean isRootNodeShown()
			{
				return false;
			}
		};
	}
}
