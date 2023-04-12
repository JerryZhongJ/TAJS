package dk.brics.tajs.analysis.nativeobjects;

import dk.brics.tajs.lattice.Value;
import dk.brics.tajs.util.AnalysisException;

public class CTypeChecker {
    public static boolean check(Value arg, String ctype){
        switch(ctype){
        case "number":
            return !arg.isMaybeOtherThanNum() && arg.isMaybeNum();
            case "string":
                return !arg.isMaybeOtherThanStr() && arg.isMaybeFuzzyStrOrSymbol();
            case "boolean":
                return !arg.isMaybeOtherThanBool() && arg.isMaybeAnyBool();
            default:
                throw new AnalysisException("Not implemented in CTypeChecker for " + ctype);
        }
    }
}
