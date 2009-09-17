package com.novocode.squery.combinator.basic

import java.sql.PreparedStatement
import com.novocode.squery.StatementInvoker
import com.novocode.squery.{Invoker, MappedInvoker, UnitInvokerMixin}
import com.novocode.squery.combinator.{Query, ColumnBaseU, NamingContext}
import com.novocode.squery.session.{Session, PositionedParameters, PositionedResult, ReadAheadIterator, CloseableIterator}

class BasicQueryInvoker[+R <: ColumnBaseU](q: Query[R], profile: BasicProfile)
  extends StatementInvoker[Unit, R#Value] with UnitInvokerMixin[R#Value] {

  protected lazy val built = profile.buildSelectStatement(q, NamingContext())

  def selectStatement = getStatement

  protected def getStatement = built.sql

  protected def setParam(param: Unit, st: PreparedStatement): Unit = built.setter(new PositionedParameters(st), null)

  protected def extractValue(rs: PositionedResult): R#Value = q.value.getResult(profile, rs)
}
