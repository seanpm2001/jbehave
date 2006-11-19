/* This file was generated by SableCC (http://www.sablecc.org/). */

package jbehave.core.story.codegen.sablecc.node;

import java.util.*;
import jbehave.core.story.codegen.sablecc.analysis.*;

public final class APhrase extends PPhrase
{
    private final LinkedList _wordOrSpace_ = new TypedLinkedList(new WordOrSpace_Cast());

    public APhrase()
    {
    }

    public APhrase(
        List _wordOrSpace_)
    {
        {
            this._wordOrSpace_.clear();
            this._wordOrSpace_.addAll(_wordOrSpace_);
        }

    }
    public Object clone()
    {
        return new APhrase(
            cloneList(_wordOrSpace_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAPhrase(this);
    }

    public LinkedList getWordOrSpace()
    {
        return _wordOrSpace_;
    }

    public void setWordOrSpace(List list)
    {
        _wordOrSpace_.clear();
        _wordOrSpace_.addAll(list);
    }

    public String toString()
    {
        return ""
            + toString(_wordOrSpace_);
    }

    void removeChild(Node child)
    {
        if(_wordOrSpace_.remove(child))
        {
            return;
        }

    }

    void replaceChild(Node oldChild, Node newChild)
    {
        for(ListIterator i = _wordOrSpace_.listIterator(); i.hasNext();)
        {
            if(i.next() == oldChild)
            {
                if(newChild != null)
                {
                    i.set(newChild);
                    oldChild.parent(null);
                    return;
                }

                i.remove();
                oldChild.parent(null);
                return;
            }
        }

    }

    private class WordOrSpace_Cast implements Cast
    {
        public Object cast(Object o)
        {
            PWordOrSpace node = (PWordOrSpace) o;

            if((node.parent() != null) &&
                (node.parent() != APhrase.this))
            {
                node.parent().removeChild(node);
            }

            if((node.parent() == null) ||
                (node.parent() != APhrase.this))
            {
                node.parent(APhrase.this);
            }

            return node;
        }
    }
}
