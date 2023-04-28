package dk.brics.tajs.analysis.nativeobjects;

import dk.brics.tajs.lattice.Value;
import dk.brics.tajs.util.AnalysisException;

public class CTypeChecker {
    public static boolean check(Value arg, String ctype){
        switch(ctype){
        case "number":
            return !arg.isMaybeOtherThanNum() && (arg.isMaybeNum() || arg.isMaybeSingleNum());
        case "string":
            return !arg.isMaybeOtherThanStr() && (arg.isMaybeFuzzyStrOrSymbol() || arg.isMaybeSingleStrOrSymbol());
        case "boolean":
            return !arg.isMaybeOtherThanBool() && (arg.isMaybeAnyBool() || arg.isMaybeFalse() || arg.isMaybeTrue());
        default:
            throw new AnalysisException("Not implemented in CTypeChecker for " + ctype);
        }
    }
}
