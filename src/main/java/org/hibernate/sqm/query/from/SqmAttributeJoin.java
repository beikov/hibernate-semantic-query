/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: Apache License, Version 2.0
 * See the LICENSE file in the root directory or visit http://www.apache.org/licenses/LICENSE-2.0
 */
package org.hibernate.sqm.query.from;

import org.hibernate.sqm.SemanticQueryWalker;
import org.hibernate.sqm.domain.DomainReference;
import org.hibernate.sqm.domain.EntityReference;
import org.hibernate.sqm.query.JoinType;
import org.hibernate.sqm.query.expression.domain.AttributeBinding;
import org.hibernate.sqm.query.predicate.SqmPredicate;

import org.jboss.logging.Logger;

/**
 * Models a join based on a mapped attribute reference.
 *
 * @author Steve Ebersole
 */
public class SqmAttributeJoin
		extends AbstractJoin
		implements SqmQualifiedJoin {
	private static final Logger log = Logger.getLogger( SqmAttributeJoin.class );

	private final AttributeBinding attributeBinding;
	private final EntityReference intrinsicSubclassIndicator;
	private final String fetchParentAlias;

	private SqmPredicate onClausePredicate;

	public SqmAttributeJoin(
			FromElementSpace containingSpace,
			AttributeBinding attributeBinding,
			String uid,
			String alias,
			EntityReference intrinsicSubclassIndicator,
			String sourcePath,
			JoinType joinType,
			String fetchParentAlias) {
		super(
				containingSpace,
				uid,
				alias,
				attributeBinding,
				intrinsicSubclassIndicator,
				sourcePath,
				joinType
		);
		this.attributeBinding = attributeBinding;
		this.intrinsicSubclassIndicator = intrinsicSubclassIndicator;
		this.fetchParentAlias = fetchParentAlias;

		attributeBinding.injectAttributeJoin( this );
	}

	public AttributeBinding getAttributeBinding() {
		return attributeBinding;
	}

	@Override
	public AttributeBinding getDomainReferenceBinding() {
		return getAttributeBinding();
	}

	@Override
	public EntityReference getIntrinsicSubclassIndicator() {
		return intrinsicSubclassIndicator;
	}

	public String getFetchParentAlias() {
		return fetchParentAlias;
	}

	@Override
	public SqmPredicate getOnClausePredicate() {
		return onClausePredicate;
	}

	public void setOnClausePredicate(SqmPredicate predicate) {
		log.tracef(
				"Setting join predicate [%s] (was [%s])",
				predicate.toString(),
				this.onClausePredicate == null ? "<null>" : this.onClausePredicate.toString()
		);

		this.onClausePredicate = predicate;
	}

	@Override
	public DomainReference getExpressionType() {
		return attributeBinding.getAttribute();
	}

	@Override
	public DomainReference getInferableType() {
		return attributeBinding.getAttribute();
	}

	@Override
	public <T> T accept(SemanticQueryWalker<T> walker) {
		return walker.visitQualifiedAttributeJoinFromElement( this );
	}
}
