

# Blog

Spring Boot 博客系统代码题


## 目录

- [实现要求](#实现要求)
- [git](#git)
- [文件目录说明](#文件目录说明)
- [实现思路](#实现思路)
  - [1、Token机制](#1、Token机制)
  - [2、从请求头获取Token,使用拦截器验证Token](#2、从请求头获取Token,使用拦截器验证Token)
  - [3、密码加密](#3、密码加密)
- [注意事项](#注意事项)
- [部署](#部署)



### 实现要求

1. 实现一个简单的 token 生成机制（也可以使用现成的token生成机制）
2. 在需要认证的接口中，从请求头获取并验证 token
3. 实现一个简单的拦截器或过滤器来处理认证逻辑
4. 注意数据的安全性，如密码加密存储
5. 使用 Docker 部署你的应用，并附上部署说明
6. 无前端需求

### git
新建blog文件夹，在该文件夹中git 
pull 仓库地址
```sh
git pull 仓库地址
```

### 文件目录说明

```
com.xtwl    寻途未来
├── config
│  ├── WebMvcConfiguration    配置类，注册拦截器，配置swagger...
├── controller
│  ├── BlogController
│  ├── UserController
├── interceptor
│  ├── JwtTokenUserInterceptor    拦截器
├── mapper
│  ├── BlogMapper
│  ├── UserMapper
├── pojo
│  ├── /dto/    前端传输到后端的数据模型
│  │  ├── BlogInsertDTO    
│  │  └── BlogUpdateDTO    
│  │  └── UserDTO   
│  │  └── UserLoginDTO    
│  │  └── UserRegisterDTO
│  ├── /entity/    数据库实体类
│  │  ├── Blog    文章
│  │  └── User    用户
│  ├── /vo/    返回前端的数据模型
│  │  ├── UserLoginVO
│  │  └── UserVO
├── result  
│  ├── Result    统一返回的结果类
├── service
│  ├── /impl/    
│  │  ├── BlogServiceImpl
│  │  └── UserServiceImpl
│  ├── BlogService
│  ├── UserService
├── utils
│  ├── JwtUtil    jwt工具类
│  ├── UserContext    使用ThreadLocal
├── BlogApplication    启动类


```


### 实现思路
#### **1、Token机制**
JwtUtil类提供了使用HS256算法生成和解析JSON Web Tokens (JWT) 的实用方法
1. Token生成
- 签名算法
   - 使用HS256算法来进行签名，也就是JWT的header部分
  
- 生成JWT的时间
   - 计算Token的过期时间：通过当前时间加上指定的过期时间（毫秒），得到Token的过期时间

- 设置JWT的body
    - 创建JWT的构建器 (JwtBuilder)，并进行以下设置：
    - 私有声明: 通过 setClaims(claims) 方法设置自定义的声明信息。这些声明信息是传入的 claims 参数中的数据。
    - 签名和秘钥: 使用 signWith(signatureAlgorithm, secretKey.getBytes(StandardCharsets.UTF_8)) 方法设置签名算法和签名使用的秘钥。秘钥通过将 secretKey 字符串转换为UTF-8字节数组来生成。
    - 过期时间: 通过 setExpiration(exp) 方法设置Token的过期时间。
- 生成并返回Token
    - 使用 builder.compact() 方法将JWT构建器生成的Token序列化为一个紧凑的字符串格式，并返回这个Token
 
2. Token解析
- 得到DefaultJwtParser
   - 使用 Jwts.parser() 方法创建一个默认的JWT解析器 (DefaultJwtParser)
  
- 设置签名的秘钥
   - 通过 setSigningKey(secretKey.getBytes(StandardCharsets.UTF_8)) 方法设置解析Token时使用的签名秘钥。秘钥通过将 secretKey 字符串转换为UTF-8字节数组来生成

- 解析Token
    - 使用 parseClaimsJws(token).getBody() 方法解析传入的加密后的Token (token) 并获取其中的声明信息 (Claims)
  

#### *2、从请求头获取Token,使用拦截器验证Token**

```sh
    //在获取当前用户信息接口
    @GetMapping("/me")
    @ApiOperation("查询用户")
    public Result query(@RequestParam String username){}
```

- 在获取当前用户信息接口，需要从请求头中获取token
- 请求到达后端首先会通过WebMvcConfiguration中注册的拦截器和配置的路径选择是否放行
```sh
    /**
     * 注册自定义拦截器
     *
     * @param registry
     */
    protected void addInterceptors(InterceptorRegistry registry) {
        log.info("开始注册自定义拦截器...");
        registry.addInterceptor(jwtTokenUserInterceptor)
                .addPathPatterns("/api/**")
                .excludePathPatterns("/api/auth/login")
                .excludePathPatterns("/api/auth/register");
    }
```
- 上述请求到达拦截器后，判断当前拦截到的是Controller的方法还是其他资源
- 从请求头中获取token
```sh
        String token = request.getHeader("token");
```
- 使用JwtUtil解析token获得claims，得到user_id 、username等信息

```sh
    //创建新文章接口
    @PostMapping
    @ApiOperation("创建文章")
    public Result insert(@RequestBody BlogInsertDTO blogInsertDTO){}
```
对于创建文章接口，当请求进入拦截器后，通过LocalThread方法保存user_id、username等信息
```sh
public class UserContext {

    public static ThreadLocal<Long> idThreadLocal = new ThreadLocal<Long>();
    public static ThreadLocal<String> usernameThreadLocal = new ThreadLocal<String>();

    public static void setCurrentId(Long id) {
        idThreadLocal.set(id);
    }

    public static Long getCurrentId() {
        return idThreadLocal.get();
    }

    public static void setUsername(String username) {
        usernameThreadLocal.set(username);
    }

    public static String getUsername() {
        return usernameThreadLocal.get();
    }
}
```
```sh
    //拦截器中校验token，解析token获得claims后，使用LocalThread获取user_id、username
    UserContext.setCurrentId(userId);
    UserContext.setUsername(username);
```

#### **3、密码加密**
调用用户登录、注册接口时，用户输入的密码到达后端后使用md5加密存入数据库
以用户注册为例：
```sh
    public void register(UserRegisterDTO userRegisterDTO) {
        String username = userRegisterDTO.getUsername();

        User user = userMapper.getByUsername(username);
        if (user != null) {
            throw new RuntimeException("用户名已存在");
        }
        User newUser = new User();

        newUser.setUsername(username);

        //从DTO对象获得密码后使用DigestUtils提供的md5对密码加密
        String password = userRegisterDTO.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());

        //加密后的密码存入newUser中
        newUser.setPassword(password);

        String email = userRegisterDTO.getEmail();
        newUser.setEmail(email);
        newUser.setCreated(LocalDateTime.now());
        newUser.setLast_modified(LocalDateTime.now());
        //调用mapper写入数据库
        userMapper.insert(newUser);
    }
```

### 注意事项
- 习惯性开启驼峰命名，导致user_id在读取时变为userId,找不到数据库中user_id 
### 部署
- 使用docker-compose.yml一次性配置好后端和数据库
