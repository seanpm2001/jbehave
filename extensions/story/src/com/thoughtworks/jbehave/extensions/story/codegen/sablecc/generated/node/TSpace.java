/* This file was generated by SableCC (http://www.sablecc.org/). */

package com.thoughtworks.jbehave.extensions.story.codegen.sablecc.generated.node;

import com.thoughtworks.jbehave.extensions.story.codegen.sablecc.generated.analysis.*;

public final class TSpace extends Token
{
    public TSpace()
    {
        super.setText(" ");
    }

    public TSpace(int line, int pos)
    {
        super.setText(" ");
        setLine(line);
        setPos(pos);
    }

    public Object clone()
    {
      return new TSpace(getLine(), getPos());
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseTSpace(this);
    }

    public void setText(String text)
    {
        throw new RuntimeException("Cannot change TSpace text.");
    }
}
