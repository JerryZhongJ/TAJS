obj = require("addon");
var b = "str"
obj.checkInt(-1, -1.1)
obj.checkUInt(-0, -1)
obj.checkDouble(1.1, "a")
obj.checkStr("1", 1)
obj.checkBool(true, "true");

