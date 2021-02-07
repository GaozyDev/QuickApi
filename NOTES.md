# SpringBoot 快速入门

## 1 项目配置
单个配置 @Value
对象配置 @ConfigurationProperties
```
// application.yml
limit:
  minMoney: 1
  maxMoney: 9999
  description: 最少要发${limit.minMoney}元, 最多${limit.maxMoney}元

// LimitConfig.java
@Component
@ConfigurationProperties(prefix = "limit")
public class LimitConfig {

	private BigDecimal minMoney;

	private BigDecimal maxMoney;

	private String description;

	// ......
}
```
使用启动命令区分开发环境和线上环境

## 2 Controller
类注解
@Controller + @ResponseBody = @RestController 请求处理类 + 返回格式
@RequestMapping(path) 统一为请求设置路径
```
@RestController
public class HelloController {
}
```
方法注解
@GetMapping(path) Get 请求方式
@PostMapping(path) Post 请求方式

参数注解
@PathVariable 获取url中的数据
@RequestParam 获取请求参数的值
```
@PostMapping("/say")
public String say(@RequestParam(value = "id", required = false, defaultValue = "0") Integer myId) {
    return "id:" + myId;
}
```
## 3 数据库
SpringDataJpa
```
// application.yml
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://127.0.0.1:3306/luckymoney?characterEncoding=utf-8
    username: root
    password: 123456
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
    
// LuckmoneyRepository.java
public interface LuckmoneyRepository extends JpaRepository<Luckymoney, Integer> {
}    
```
## 4 事务
@Transactional 开启事务，同时注意数据库也要支持事务
```
@Service
public class LuckymoneyService {

    @Autowired
    private LuckmoneyRepository repository;

    @Transactional
    public void createTwo() {
        // ......
    }
}

```
## 5 表单验证
@Valid，BindingResult 返回验证信息
```
// BuyerOrderController.java
@PostMapping("/create")
public ResultVO<Map<String, String>> create(@Valid OrderForm orderForm,
                                            BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
        log.error("【创建订单】参数不正确, orderForm={}", orderForm);
        throw new SellException(ResultEnum.PARAM_ERROR.getCode(),
                bindingResult.getFieldError().getDefaultMessage());
    }
    // ......
}

// OrderForm.java
@Data
public class OrderForm {

    /**
     * 买家手机号
     */
    @NotEmpty(message = "手机号必填")
    private String phone;
    // ......
}
```

## 6 AOP
@Pointcut 方法标记
@Before 切面之上执行
@After 切面之下执行
RequestContextHolder.getRequestAttributes() 获取请求信息
@doAfterReturning 获取返回数据
```
@Aspect
@Component
public class SellerAuthorizeAspect {

    @Pointcut("execution(public * com.imooc.controller.Seller*.*(..))" +
    "&& !execution(public * com.imooc.controller.SellerUserController.*(..))")
    public void verify() {}

    @Before("verify()")
    public void doVerify() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        // ......
    }
    
    @After("verify()")
    public void doAfterReturning() {
        
    }
    
    @doAfterReturning(returning = "object", pointcut = "verify()")
    public void doAfterReturning(Object object) {
    }
}
```
## 7 统一异常处理
① 统一返回格式，code message data

② 在逻辑中抛出异常，之后统一捕获异常
类注解
@ControllerAdvice
方法注解
@ExceptionHandler(value = Exception.class)

③ 自定义异常 + 枚举

## 8 单元测试
Service 测试
类注解
@RunWith(SpringRunner.class)
@SpringBootTest
方法注解
@Test

Controller 测试涉及到URL，传参，所以还需要下面这些
类注解
@AutoConfigureMockMvc
参数注解
@Autowired
private MockMvc mvc
使用 MockMvc 的 API 进行测试逻辑