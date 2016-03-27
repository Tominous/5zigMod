package org.h2.command.ddl;

import org.h2.constraint.Constraint;
import org.h2.engine.Database;
import org.h2.engine.Session;
import org.h2.engine.User;
import org.h2.message.DbException;
import org.h2.schema.Schema;

public class AlterTableDropConstraint
  extends SchemaCommand
{
  private String constraintName;
  private final boolean ifExists;
  
  public AlterTableDropConstraint(Session paramSession, Schema paramSchema, boolean paramBoolean)
  {
    super(paramSession, paramSchema);
    this.ifExists = paramBoolean;
  }
  
  public void setConstraintName(String paramString)
  {
    this.constraintName = paramString;
  }
  
  public int update()
  {
    this.session.commit(true);
    Constraint localConstraint = getSchema().findConstraint(this.session, this.constraintName);
    if (localConstraint == null)
    {
      if (!this.ifExists) {
        throw DbException.get(90057, this.constraintName);
      }
    }
    else
    {
      this.session.getUser().checkRight(localConstraint.getTable(), 15);
      this.session.getUser().checkRight(localConstraint.getRefTable(), 15);
      this.session.getDatabase().removeSchemaObject(this.session, localConstraint);
    }
    return 0;
  }
  
  public int getType()
  {
    return 14;
  }
}
