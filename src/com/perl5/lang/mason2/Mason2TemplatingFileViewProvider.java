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

package com.perl5.lang.mason2;

import com.intellij.lang.Language;
import com.intellij.lang.LanguageParserDefinitions;
import com.intellij.lang.ParserDefinition;
import com.intellij.lang.StdLanguages;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.MultiplePsiFilesPerDocumentFileViewProvider;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.impl.source.PsiFileImpl;
import com.intellij.psi.templateLanguages.TemplateLanguageFileViewProvider;
import com.perl5.lang.mason2.elementType.Mason2ElementTypes;
import com.perl5.lang.pod.PodLanguage;
import gnu.trove.THashSet;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Set;

/**
 * Created by hurricup on 20.12.2015.
 */
public class Mason2TemplatingFileViewProvider extends MultiplePsiFilesPerDocumentFileViewProvider implements TemplateLanguageFileViewProvider, Mason2ElementTypes
{
	private static final THashSet<Language> ourRelevantLanguages =
			new THashSet<Language>(Arrays.asList(
					StdLanguages.HTML,
					Mason2TemplatingLanguage.INSTANCE,
					PodLanguage.INSTANCE
			));


	public Mason2TemplatingFileViewProvider(final PsiManager manager, final VirtualFile virtualFile, final boolean physical)
	{
		super(manager, virtualFile, physical);
	}

	@Override
	@NotNull
	public Language getBaseLanguage()
	{
		return Mason2TemplatingLanguage.INSTANCE;
	}

	@Override
	@NotNull
	public Set<Language> getLanguages()
	{
		return ourRelevantLanguages;
	}

	@Override
	@Nullable
	protected PsiFile createFile(@NotNull final Language lang)
	{
		if (lang != PodLanguage.INSTANCE && lang != getBaseLanguage() && lang != getTemplateDataLanguage())
			return null;

		final ParserDefinition parserDefinition = LanguageParserDefinitions.INSTANCE.forLanguage(lang);

		if (parserDefinition != null)
		{
			final PsiFileImpl file = (PsiFileImpl) parserDefinition.createFile(this);
			if (lang == getTemplateDataLanguage())
			{
				file.setContentElementType(MASON_HTML_TEMPLATE_DATA);
			}
			else if (lang == PodLanguage.INSTANCE)
			{
				file.setContentElementType(MASON_POD_TEMPLATE_DATA);
			}
			return file;
		}
		return null;
	}

	@Override
	protected Mason2TemplatingFileViewProvider cloneInner(final VirtualFile copy)
	{
		return new Mason2TemplatingFileViewProvider(getManager(), copy, false);
	}

	@Override
	@NotNull
	public Language getTemplateDataLanguage()
	{
		return StdLanguages.HTML;
	}

}
