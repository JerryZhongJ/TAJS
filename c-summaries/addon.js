function checkInt(a, b){
    return 0;
}

function checkUInt(a, b){
    return 0;
}

function checkDouble(a, b){
    return 0;
}

function checkStr(a, b) {
    return 0;
}

function checkBool(a, b) {
    return 0;
}

module.exports = {
    checkInt: TAJS_CFunction(checkInt, "int", "int"),
    checkUInt: TAJS_CFunction(checkUInt, "uint", "uint"),
    checkDouble: TAJS_CFunction(checkDouble, "double", "double"),
    checkStr: TAJS_CFunction(checkStr, "string", "string"),
    checkBool: TAJS_CFunction(checkBool, "boolean", "boolean")
}