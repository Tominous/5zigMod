package org.h2.expression;

import org.h2.command.Parser;
import org.h2.engine.Session;
import org.h2.message.DbException;
import org.h2.table.ColumnResolver;
import org.h2.table.TableFilter;
import org.h2.value.Value;

public class Variable
  extends Expression
{
  private final String name;
  private Value lastValue;
  
  public Variable(Session paramSession, String paramString)
  {
    this.name = paramString;
    this.lastValue = paramSession.getVariable(paramString);
  }
  
  public int getCost()
  {
    return 0;
  }
  
  public int getDisplaySize()
  {
    return this.lastValue.getDisplaySize();
  }
  
  public long getPrecision()
  {
    return this.lastValue.getPrecision();
  }
  
  public String getSQL()
  {
    return "@" + Parser.quoteIdentifier(this.name);
  }
  
  public int getScale()
  {
    return this.lastValue.getScale();
  }
  
  public int getType()
  {
    return this.lastValue.getType();
  }
  
  public Value getValue(Session paramSession)
  {
    this.lastValue = paramSession.getVariable(this.name);
    return this.lastValue;
  }
  
  public boolean isEverything(ExpressionVisitor paramExpressionVisitor)
  {
    switch (paramExpressionVisitor.getType())
    {
    case 0: 
    case 1: 
    case 3: 
    case 4: 
    case 5: 
    case 6: 
    case 7: 
    case 8: 
    case 9: 
      return true;
    case 2: 
      return false;
    }
    throw DbException.throwInternalError("type=" + paramExpressionVisitor.getType());
  }
  
  public void mapColumns(ColumnResolver paramColumnResolver, int paramInt) {}
  
  public Expression optimize(Session paramSession)
  {
    return this;
  }
  
  public void setEvaluatable(TableFilter paramTableFilter, boolean paramBoolean) {}
  
  public void updateAggregate(Session paramSession) {}
  
  public String getName()
  {
    return this.name;
  }
}
