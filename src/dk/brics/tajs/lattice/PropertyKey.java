/*
 * Copyright 2009-2020 Aarhus University
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dk.brics.tajs.lattice;

import dk.brics.tajs.util.AnalysisException;
import dk.brics.tajs.util.Canonicalizer;
import dk.brics.tajs.util.Collections;
import dk.brics.tajs.util.Collectors;
import dk.brics.tajs.util.DeepImmutable;
import dk.brics.tajs.util.Strings;

import javax.annotation.Nonnull;
import java.util.Set;

/**
 * A fixed property key.
 */
abstract public class PropertyKey implements DeepImmutable { // XXX: rename class (and other occurrences of "PKey")?

    /**
     * Converts this property key to a {@link Value}.
     */
    public abstract Value toValue();

    /**
     * Checks whether this property key is a numeric index.
     */
    public abstract boolean isNumeric();

    /**
     * Checks whether this property matches the given value.
     */
    public abstract boolean isMaybeValue(StringOrSymbol v);

    abstract public boolean equals(Object o);

    abstract public int hashCode();

    /**
     * Returns a string representation of this property key, with special characters escaped.
     */
    abstract public String toStringEscaped();

    /**
     * Returns a property key describing the given singleton property key value.
     * @throws AnalysisException if not a singleton
     */
    public static PropertyKey make(StringOrSymbol singleton_pkeys) {
        if (singleton_pkeys.isMaybeSingleStr())
            return StringPropertyKey.make(singleton_pkeys.getStr());
        Set<ObjectLabel> symbols = singleton_pkeys.getSymbols();
        if (symbols.size() == 1)
            return SymbolPKey.make(symbols.iterator().next());
        throw new AnalysisException("Expected singleton property keys");
    }

    /**
     * Returns a copy of this property key where the given object label has been replaced.
     *
     * @param oldlabel The object label to replace.
     * @param newlabel The object label to replace oldlabel with.
     */
    abstract public PropertyKey replaceObjectLabel(ObjectLabel oldlabel, ObjectLabel newlabel);

    /**
     * Returns true if this property key contains the given object label.
     */
    abstract public boolean containsObjectLabel(ObjectLabel objlabel);

    /**
     * Constructs a property key as a copy of this value but with object labels renamed.
     */
    abstract public Set<PropertyKey> rename(Renamings s);

    /**
     * Property key for fixed string.
     */
    public static final class StringPropertyKey extends PropertyKey {

        public static PropertyKey __PROTO__;

        public static PropertyKey PROTOTYPE;

        public static PropertyKey LENGTH;

        public static void reset() {
            __PROTO__ = make("__proto__");
            PROTOTYPE = make("prototype");
            LENGTH = make("length");
        }

        static {
            reset();
        }

        private String str;

        private int hashcode;

        private StringPropertyKey(String str) {
            this.str = str;
            this.hashcode = str.hashCode() + this.getClass().hashCode();
        }

        /**
         * Constructs a property key for a fixed string property.
         */
        public static StringPropertyKey make(String str) {
            return Canonicalizer.get().canonicalize(new StringPropertyKey(str));
        }

        @Override
        public boolean isNumeric() {
            return Strings.isNumeric(str);
        }

        @Override
        public boolean isMaybeValue(StringOrSymbol v) {
            return v.isMaybeExactStr(str);
        }

        /**
         * Returns the string.
         */
        public String getStr() {
            return str;
        }

        @Override
        public Value toValue() {
            return Value.makeStr(str);
        }

        @Override
        public String toString() {
            return str;
        }

        @Override
        public String toStringEscaped() {
            return Strings.escape(str, true);
        }

        @Override
        public boolean equals(Object obj) {
            if (!Canonicalizer.get().isCanonicalizing())
                return this == obj;
            return obj instanceof StringPropertyKey && getStr().equals(((StringPropertyKey)obj).getStr());
        }

        @Override
        public int hashCode() {
            return hashcode;
        }

        public StringPropertyKey replaceObjectLabel(ObjectLabel oldlabel, ObjectLabel newlabel) {
            return this;
        }

        public boolean containsObjectLabel(ObjectLabel objlabel) {
            return false;
        }

        public Set<PropertyKey> rename(Renamings s) {
            return Collections.singleton(this);
        }
    }

    /**
     * Property key for Symbol.
     */
    public static final class SymbolPKey extends PropertyKey {

        private ObjectLabel objlabel;

        private int hashcode;

        private SymbolPKey(ObjectLabel objlabel) {
            this.objlabel = objlabel;
            this.hashcode = objlabel.hashCode() + this.getClass().hashCode();
        }

        /**
         * Constructs a property key for a Symbol.
         */
        public static SymbolPKey make(ObjectLabel objlabel) {
            return Canonicalizer.get().canonicalize(new SymbolPKey(objlabel));
        }

        /**
         * Returns the object label.
         */
        public ObjectLabel getObjectLabel() {
            return objlabel;
        }

        @Override
        public Value toValue() {
            return Value.makeObject(objlabel);
        }

        @Override
        public boolean isNumeric() {
            return false;
        }

        @Override
        public boolean isMaybeValue(StringOrSymbol v) {
            return v.getSymbols().contains(objlabel);
        }

        @Override
        public String toString() {
            return "Symbol(" + objlabel + ")";
        }

        @Override
        public String toStringEscaped() {
            return toString();
        }

        @Override
        public boolean equals(Object obj) {
            if (!Canonicalizer.get().isCanonicalizing())
                return this == obj;
            return obj instanceof SymbolPKey && getObjectLabel().equals(((SymbolPKey)obj).getObjectLabel());
        }

        @Override
        public int hashCode() {
            return hashcode;
        }

        public SymbolPKey replaceObjectLabel(ObjectLabel oldlabel, ObjectLabel newlabel) {
            return objlabel.equals(oldlabel) ? make(newlabel) : this;
        }

        public boolean containsObjectLabel(ObjectLabel ol) {
            return objlabel.equals(ol);
        }

        public Set<PropertyKey> rename(Renamings s) {
            return s.rename(Collections.singleton(objlabel)).stream().map(SymbolPKey::make).collect(Collectors.toSet());
        }
    }

    public static class Comparator implements java.util.Comparator<PropertyKey> {

        @Override
        public int compare(@Nonnull PropertyKey o1, @Nonnull PropertyKey o2) {
            if (o1 instanceof StringPropertyKey) {
                if (o2 instanceof StringPropertyKey) {
                    return ((StringPropertyKey)o1).getStr().compareTo(((StringPropertyKey)o2).getStr());
                } else {
                    return 1;
                }
            } else if (o2 instanceof StringPropertyKey) {
                return -1;
            } else {
                return ObjectLabel.Comparator.compareStatic(((SymbolPKey)o1).getObjectLabel(), ((SymbolPKey)o2).getObjectLabel());
            }
        }
    }
}