/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.sqm.query.expression;

import org.hibernate.sqm.SemanticQueryWalker;
import org.hibernate.sqm.domain.StandardBasicTypeDescriptors;
import org.hibernate.sqm.domain.TypeDescriptor;

/**
 * @author Steve Ebersole
 */
public class LiteralLongExpression extends AbstractLiteralExpressionImpl<Long> {
	public LiteralLongExpression(Long value) {
		super( value );
	}

	@Override
	public TypeDescriptor getTypeDescriptor() {
		return StandardBasicTypeDescriptors.INSTANCE.LONG;
	}

	@Override
	public <T> T accept(SemanticQueryWalker<T> walker) {
		return walker.visitLiteralLongExpression( this );
	}
}