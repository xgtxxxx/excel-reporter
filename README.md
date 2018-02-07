## 这个项目主要是为了简化导出excel的工作

### 用法
#### 1.首先需要初始化一个PowerExcelService实体(DefaultPowerExcelService)
``` java
public interface PowerExcelService {
    <T> void create(String path, List<T> models);
    <T> void create(OutputStream outputStream, List<T> models);
    <T> void create(String path, List<T> models, Template template);
    <T> void create(OutputStream outputStream, List<T> models, Template template);
    <T> void create(String path, Sheets sheets);
    <T> void create(OutputStream outputStream, Sheets sheets);
}
```

#### 2.最简单的导出实例
``` java
        final String path = "D:/test.xlsx";
        final DefaultPowerExcelService excelService = new DefaultPowerExcelService();
        excelService.create(path, Arrays.asList(
            new TestModel("111", "test1", 30, new MyDate()),
            new TestModel("222", "test2", 33, new MyDate()),
            new TestModel("333", "test3", 33, new MyDate()),
            new TestModel("444", "test4", 55, new MyDate()),
            new TestModel("555", "test5", 33, new MyDate()),
            new TestModel("666", "test6", 33, new MyDate())));
```
TestModel.java
```
    public class TestModel extends Model {
        private String id;
        private String name;
        private int age;
        private MyDate myDate;
        public TestModel(final String id, final String name, final int age) {
            this.age = age;
            this.name = name;
            super.setId(id);
        }
    }
```

#### 3. 相对复杂的例子
``` java
@BgColors(colors = {
    @BgColor(color = Font.COLOR_RED, rowIndex = 1, loopSkipLength = 2, stepLength = 2),
    @BgColor(color = 5, rowIndex = 0)
})
@Title("TEST")
@CustomConverter(converter = DateConverter.class, applyTo = Date.class)
@Region(startRow = 2, endRow = 3, startColumn = 1, endColumn = 1, loop = true)
public class TestModel extends Model {
    @Title("Name")
    @OrderIndex(3)
    private String name;
    @Title("Age")
    @CustomStyle(TestStyle.class)
    private int age;
    private MyDate myDate;

    public TestModel(final String id, final String name, final int age) {
        this.age = age;
        this.name = name;
        super.setId(id);
    }
}
```

<strong>@BgColor：</strong><br>
color -- 背景色<br>
rowIndex -- 起始行，默认-1，表示全部填充<br>
loopSkipLength -- 循环间隔行数，默认0，不循环<br>
stepLength -- 填充背景色的行数, 默认1<br>

<strong>@CustomConverter：</strong><br>
converter: Converter实现类<br>
fields: fields实用于注解在class上，表示这些fields需要用这个converter来转换<br>
applyTo: 实用于注解在class上，表示这种类型的值才能被转换(如Date.class),那么所有Date属性将会被转换<br>
applyToAll: 注解于class上，表示所有属性都会被转换<br>

<strong>@Title：</strong><br>
注解在类上：sheet名字，如果重名，则默认在名字后面加上123...<br>
注解在属性上：属性名，在excel导出时第一行为属性名<br>

<strong>@Region：</strong><br>
注解在类上，用于合并单元格，loop如果设置为true，则会纵向循环合并<br>

<strong>@OrderIndex：</strong><br>
属性顺序

<strong>@CustomStyle：</strong><br>
自定义样式，用于属性