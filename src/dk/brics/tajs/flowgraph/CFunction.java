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
    
    public boolean checkType(int index, Value arg) {
        switch (types[index]) {
        case INT:
            
        case UINT:
            // TODO
        case DOUBLE:
            return !arg.isMaybeOtherThanNum() && (arg.isMaybeNum() || arg.isMaybeSingleNum());
        case STRING:
            return !arg.isMaybeOtherThanStr() && (arg.isMaybeFuzzyStrOrSymbol() || arg.isMaybeSingleStrOrSymbol());
        case BOOLEAN:
            return !arg.isMaybeOtherThanBool() && (arg.isMaybeAnyBool() || arg.isMaybeFalse() || arg.isMaybeTrue());
        default:
            throw new AnalysisException("Not implemented in CTypeChecker for " + types[index]);
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
