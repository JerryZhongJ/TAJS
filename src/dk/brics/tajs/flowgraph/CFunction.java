package dk.brics.tajs.flowgraph;

import dk.brics.tajs.lattice.Value;
import dk.brics.tajs.util.AnalysisException;

public class CFunction extends Function{
    private enum CType {
        UINT,
        INT,
        DOUBLE,
        STRING,
        BOOLEAN;

        @Override
        public String toString() {
            switch (this) {
            case UINT:
                return "uint";
            case INT:
                return "int";
            case DOUBLE:
                return "double";
            case STRING:
                return "string";
            case BOOLEAN:
                return "boolean";
            default:
                throw new AnalysisException("Not implemented in CTypeChecker for " + this);
            }
        }
    };

    public class TypeCheckFail extends Exception {
        private TypeCheckFail(String message) {
            super(message);
        }
    }

    public CType types[];
    private Function raw_function;

    public CFunction(Function f, String types[]) {
        super(f);
        this.types = new CType[types.length];
        for (int i = 0; i < types.length; i++) {
            switch (types[i]) {
            case "int":
                this.types[i] = CType.INT;
                break;
            case "uint":
                this.types[i] = CType.UINT;
                break;
            case "double":
                this.types[i] = CType.DOUBLE;
                break;
            case "string":
                this.types[i] = CType.STRING;
                break;
            case "boolean":
                this.types[i] = CType.BOOLEAN;
                break;
            default:
                throw new AnalysisException("C Type not supported: " + types[i]);
            }
        }
    }
    
    public void checkType(int index, Value arg) throws TypeCheckFail{
        
        switch (types[index]) {
        case INT:
            if (arg.isMaybeOtherThanNumInt() || (!arg.isMaybeSingleNumInt() && !arg.isMaybeNumInt()))
                throw new TypeCheckFail(String.format("Error: expected parameter '%s' of '%s' to be int.", getParameterNames().get(index), getName()));
            break;
        case UINT:
            if (arg.isMaybeOtherThanNumUInt() || (!arg.isMaybeSingleNumUInt() && !arg.isMaybeNumUInt()))
                throw new TypeCheckFail(String.format("Error: expected parameter '%s' of '%s' to be unsigned int.", getParameterNames().get(index), getName()));
            break;
        case DOUBLE:
            if (arg.isMaybeOtherThanNum() && (!arg.isMaybeSingleNum() && !arg.isMaybeNum()))
                throw new TypeCheckFail(String.format("Error: expected parameter '%s' of '%s' to be double.", getParameterNames().get(index), getName()));
            else if(arg.isMaybeSingleNumInt() || arg.isMaybeNumInt())
                throw new TypeCheckFail(String.format("Warning: expected parameter '%s' of '%s' to be unsigned double, but an interger provided.", getParameterNames().get(index), getName()));
            break;
        case STRING:
            if (arg.isMaybeOtherThanStr() || (!arg.isMaybeSingleStrOrSymbol() && !arg.isMaybeFuzzyStrOrSymbol()))
                throw new TypeCheckFail(String.format("Error: expected parameter '%s' of '%s' to be string.", getParameterNames().get(index), getName()));
            break;
        case BOOLEAN:
            if(arg.isMaybeOtherThanBool() ||( !arg.isMaybeTrue() && !arg.isMaybeFalse() && !arg.isMaybeAnyBool()))
                throw new TypeCheckFail(String.format("Error: expected parameter '%s' of '%s' to be boolean.", getParameterNames().get(index), getName()));
            break;
        
        }
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof CFunction)) {
            return false;
        }
        return this.raw_function == ((CFunction) o).raw_function;
    }
    
}
