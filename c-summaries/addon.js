function method(a, b, c){
    return 0;
}

module.exports = {
    method: TAJS_CFunction(method, "double", "string", "boolean")
}