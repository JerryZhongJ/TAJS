module.exports = {
    checkInt: TAJS_CFunction(function checkInt(a, b){}, "int", "int"),
    checkUInt: TAJS_CFunction(function checkUInt(a, b){}, "uint", "uint"),
    checkDouble: TAJS_CFunction(function checkDouble(a, b){}, "double", "double"),
    checkStr: TAJS_CFunction(function checkStr(a, b){}, "string", "string"),
    checkBool: TAJS_CFunction(function checkBool(a, b) { }, "boolean", "boolean"),
    checkObject: TAJS_CFunction(function checkObject(a, b) { }, "object", "object"),
    checkFunction: TAJS_CFunction(function checkFunction(a, b) { }, "function", "function"),
    checkArray: TAJS_CFunction(function checkArray(a, b) { }, "array", "array"),
    checkUnknown: TAJS_CFunction(function checkUnknown(a, b) { }, "unknown", "unknown"),
}