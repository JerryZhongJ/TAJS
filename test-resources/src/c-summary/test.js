obj = require("addon");
obj2 = require("./test2")
obj.checkInt(Math.ceil(3), Math.abs(-1.1))
obj.checkUInt(Math.abs(-2), Math.floor(-2.1))
obj.checkDouble(1.1, 1)
obj.checkStr(obj2.msg, 1)
obj.checkBool(true, "true");
obj.checkObject(obj2, 1);
obj.checkFunction(function (a){}, obj2);
obj.checkArray([1, 2, 3], 1);
obj.checkUnknown(obj2, 1);
obj2.meth();

