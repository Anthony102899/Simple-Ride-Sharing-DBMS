xdm在写之前注意：
	1、把src文件夹解压覆盖至你自己的project下src文件夹。
	2、只改动你自己负责的class，不要在main里面加任何内容。
	3、如果认为main的功能不够完善，我们在群里可以再讨论一下。

在写的时候注意：
	0、我在最新版本中，预设每个constructor会接受一个Connection类的对象con。由此可以在你的constructor中创建statement。（可以参考Admin.java内的写法，实测可用）
	1、对所有接受输入的语句进行exception handling。
	2、在constructor里生成GUI。
	3、在constructor内生成loop，使得用户完成某一操作后回到UI界面。
	4、接收到特定值时，break。

测试时注意：
	1、在Administrator功能完善前，手动通过ssh secure shell建立tables。
	2、在Administrator功能完善后，通过admin的interface 自动导入test cases。