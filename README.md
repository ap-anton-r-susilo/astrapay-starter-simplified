## Astrapay Starter###


### Features 

>Astrapay Starter berisikan beberapa fitur global yang dapat langsung digunakan pada setiap Project.

1. <b>LoggerConfiguration</b>: merupakan Logger yang berguna untuk melakukan log setiap <i>Incoming Request</i>.
   Logger akan menulis secara otomatis sesuai dengan structured logging yang akan dapat mudah di index pada Sumo/ELK. <br/><br/>
2. <b>GeneralExceptionAdvice</b>: digunakan untuk melakukan interceptor error yang sudah di build sesuai dengan standard Astrapay. 
   GeneralExceptionAdvice akan secara otomatis membentuk format error pada response.<br/><br/>
3. <b>GenericSpecification</b> [version 1.6+]: digunakan untuk membantu melakukan filter pada pagination repository.<br/><br/>
4. <b>ParamName Annotation</b>: Digunakan untuk melakukan mapping dari incoming request ke field dengan nama yang berbeda.<br/><br/>
5. <b>Field Exclusion in Log</b> [version 1.13+]: digunakan untuk menghilangkan value pada log di request body dan request params. <br/><br/>
6. <b>Common Use</b> [version 1.14+]: <br/>
<ul>
   <li>UriFormat.class: Digunakan untuk melakukan mapper URI dengan & dan =</li>
   <li>PageResponse: digunakan untuk Object Rest Template ketika menerima response Page</li>
</ul>
7. <b>Mapper Configuration</b>: DEPRECATED!!! Digunakan untuk melakukan konfigurasi mapping dari Data Object ke Data Object ketika system di start.
8. <b>JSON Serializer & Deserializer</b>: Digunakan untuk serialisasi dan deserialisasi tanggal dan waktu LocalDateTime dan ZonedDateTime.
9. <b>Indonesia Date Time Serializer & Deserializer</b>: Digunakan untuk serialisasi dan deserialisasi tanggal dan waktu Indonesia (+07:00).
10. <b>Code Generator and Randomize</b> [version 1.17+]: Digunakan untuk membuat code dengan random alphanumeric.
11. <b>Orika Data Converter</b>: <br/>
    Orika Converter digunakan untuk mengubah bentuk sebuah object ke object lain.
    1. AdditionalDataToListConverter, untuk melakukan convert dari AdditionalData ke List yang akan digunakan oleh Mobile
12. <b>AstraPay Signature Generator</b><br/>
    AstraPay Signature Generator digunakan untuk membuat signature yang akan digunakan untuk verifikasi apakah request 
yang diberikan oleh merchant tidak dirubah oleh hacker. Ada beberapa parameter yang digunakan, salah satunya ada <b>astrapayKey</b>, parameter ini diisi dengan value bearer token yang digunakan oleh merchant untuk mengirimkan request.
13. <b>Authentication Configuration</b><br/> 
digunakan untuk melakukan autentikasi terhadap endpoint yang sudah diberikan penjagaan berdasarkan privilege tertentu.
14. <b>Master Slave Configuration</b><br/>
digunakan jika Anda mempunyai master database dan slave database
15. <b>Snap BI Signature Generator</b><br/>
    1. Signature Auth
    2. Signature Service
16. <b>Masking Name</b><br/>
    Digunakan untuk masking nama user menggunakan pola sebagai berikut: "I Gusti Ni Adi Wijaya" akan menjadi "I Gu\**i N\* A\*i Wi\***a".
Ada beberapa parameter yang digunakan dan dapat disesuaikan sesuai kebutuhan, yaitu:
    1. <b>maskChar</b>, yaitu karakter yang digunakan untuk masking. Secara default adalah " * ".
    2. <b>maxOutputLength</b>, yaitu maksimum panjang karakter, secara default adalah 20 karakter.
17. <b>Masking Phone Number</b><br/>
    Digunakan untuk masking nomor telepon user dengan pola 4 digit pertama dan 3 digit terakhir, berikut adalah contoh hasil masking phone number:
    1. 08819928181 ketika dimasking akan menjadi 0881****181.
    2. <b>maskChar</b>, yaitu karakter yang digunakan untuk masking. Secara default adalah " * ".
    

## How to use?

### Logger Configuration
> This logger is used to automatically log every incoming request with Correlation ID for tracking log

#### How to setup ?
- Just put `void main` or `@SpringBootApplication` at package `com.astrapay`
- Astrapay Starter will automatically detect and load the default Logger Configuration of Astrapay
- Set `logging.config=classpath:logback-sit.xml` in your properties 
- Put file `logback.xml` or `logback-<env>.xml` in `resources`. <br/>
Example of `logback.xml`
```
<configuration>
    <include resource="org/springframework/boot/logging/logback/base.xml"/>

    <appender name="FILE" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <File>${APP_HOME}/logs/log_${APP_REMOTE_NAME}.json.log</File>
        <encoder class="net.logstash.logback.encoder.LogstashEncoder">
            <fieldNames>
                <timestamp>timestamp</timestamp>
                <message>msg</message>
                <thread>thread</thread>
                <logger>logger</logger>
                <version>version</version>
                <mdc>mdc</mdc>
            </fieldNames>
        </encoder>
        <rollingPolicy class="ch.qos.logback.core.rolling.SizeAndTimeBasedRollingPolicy">
            <FileNamePattern>
                ${APP_HOME}/logs/log_${APP_REMOTE_NAME}-%d{yyyy-MM-dd}.%i.json.log
            </FileNamePattern>
            <!-- each file should be at most 500MB, keep 60 days worth of history, but at most 20GB -->
            <maxFileSize>500MB</maxFileSize>
            <maxHistory>60</maxHistory>
            <totalSizeCap>20GB</totalSizeCap>
        </rollingPolicy>
    </appender>

    <root level="info">
        <appender-ref ref="FILE"/>
    </root>
</configuration>
```
- If you run from local, please set `APP_HOME` and `APP_REMOTE_NAME` in intellij. If you run on server astrapay, its will be set on Jenkins Properties

| properties | type | value
| --- | --- | ---
| APP_HOME | string | ./
| APP_REMOTE_NAME | string | [service name]


There are many possible Policy can be implement
- [Logback Policy] (http://logback.qos.ch/manual/appenders.html)


#### Properties
| properties | type | default
| --- | --- | --- |
| astrapay-starter.log.request.enabled | boolean | true

Example Log:
```
{"timestamp":"2021-07-20T19:25:01.789+07:00","version":"1","msg":"Servlet(uri=/cashin-service/topups, remoteAddress=0:0:0:0:0:0:0:1, header={sec-fetch-mode=cors, sec-gpc=1, referer=http://localhost:8102/, sec-fetch-site=same-site, accept-language=en-US,en;q=0.9, origin=http://localhost:8102, host=localhost:8005, connection=keep-alive, accept-encoding=gzip, deflate, br, accept=application/json, text/plain, */*, user-agent=Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.106 Safari/537.36, sec-fetch-dest=empty}, params={size=10, page=1}, payload={}, payloadSize=0, method=GET, contentType=null)","logger":"com.astrapay.starter.configuration.LoggerConfiguration","thread":"http-nio-8005-exec-1","level":"INFO","level_value":20000,"mdc":{"correlation_id":"86f7ce45-3681-4a8d-8f25-3fd54702786d"},"request":{"uri":"/cashin-service/topups","remoteAddress":"0:0:0:0:0:0:0:1","header":{"sec-fetch-mode":"cors","sec-gpc":"1","referer":"http://localhost:8102/","sec-fetch-site":"same-site","accept-language":"en-US,en;q=0.9","origin":"http://localhost:8102","host":"localhost:8005","connection":"keep-alive","accept-encoding":"gzip, deflate, br","accept":"application/json, text/plain, */*","user-agent":"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.106 Safari/537.36","sec-fetch-dest":"empty"},"params":{"size":"10","page":"1"},"payload":{},"payloadSize":0,"method":"GET","contentType":null}}
```

### GeneralExceptionAdvice

Currently, GeneralExceptionAdvice support to handle `Exception.class` and `BindException.class`

#### How to setup ?

- Just put `void main` or `@SpringBootApplication` at package `com.astrapay`
- Astrapay Starter will automatically detect and load the default Advice of Astrapay

#### Example Response
```aidl
{
    "timestamp": "2021-06-03T10:49:10.498+00:00",
    "status": 400,
    "error": "Bad Request",
    "message": "Validation failed for object='transactionRequestDto'. Error count: 1",
    "path": "/payment/transactions",
    "defails": [
        {
            "code": "...",
            "objectName: "...",
            "defaultMessage": "..."
        }
    ]
}
```

### GenericSpecification
- Available on version 1.6+

####  Example
`/..../topups?partnerCode=ALFAMART`
```
GenericSpecificationService genericSpecificationService = new GenericSpecificationService<Topup>();
genericSpecificationService.add(new SearchCriteria(Topup_.partnerCode.getName(), requestDto.getPartnerCode(), SearchCriteria.SearchOperation.EQUAL));

return topupRepository.findAll(genericSpecificationService, pageable).map(topup -> mapperFacade.map(topup, TopupDto.class));        
```

#### How to use Entity_. in Intellij ?
1. Add Dependency jpamodelgen
```
<!-- https://mvnrepository.com/artifact/org.hibernate/hibernate-jpamodelgen -->
<dependency>
   <groupId>org.hibernate</groupId>
   <artifactId>hibernate-jpamodelgen</artifactId>
   <version>5.5.4.Final</version>
</dependency>
```
2. Setting .iml
Pastikan ada code berikut pada .iml masing-masing project
```
<sourceFolder url="file://$MODULE_DIR$/target/generated-sources/annotations" isTestSource="false" generated="true" />
```

3. Setting Project Structure
- File > Project Structure > Modules
- Pilih `target/generated-sources/annotations`
- Klik `Sources` pada bagian atas "Mark As"

### ParamName Annotation

#### Incoming Request
```
{
    transaction_number: '123'
}
```

#### TransactionRequestDto
```
class TransactionRequestDto {
    String transactionId;
}
```

#### Old Technique
```
class TransactionRequestDto {
    String transactionId;

    public String getTransaction_number() {
        return this.transactionId;
    }

    public void setTransaction_number(String number) {
        this.transactionId = number;
    }
}
```

#### Using @ParamName
```
class TransactionRequestDto {
    @ParamName("transaction_number")
    String transactionId;
}
```

### Field Exclusion in Log
Add this property with the name of field or param you want to exclude from log

```aidl
astrapay-starter.log.request.exclude.field=pin,password
astrapay-starter.log.request.exclude.param=compare
astrapay-starter.log.request.exclude.param=data.pin,data.otp
```

#### Example Log
```
{"timestamp":"2021-09-08T15:29:26.248+07:00","version":"1","msg":"ServletDto(uri=/authentication-service/users/3/credential, remoteAddress=0:0:0:0:0:0:0:1, header={content-length=97, postman-token=612a69b2-6e8d-4ccd-97ad-5823dc0c703d, host=localhost:8000, content-type=application/json, connection=keep-alive, accept-encoding=gzip, deflate, br, user-agent=PostmanRuntime/7.28.4, accept=*/*}, params={}, payload={phoneNumber=080912312302, pin=[****], email=users@astrapay.com}, payloadSize=89, method=POST, contentType=application/json)","logger":"com.astrapay.starter.configuration.LoggerConfiguration","thread":"http-nio-8000-exec-2","level":"INFO","level_value":20000,"mdc":{"correlation_id":"5eff219c-4aaa-434e-bd6b-bb961311a831"},"request":{"uri":"/authentication-service/users/3/credential","remoteAddress":"0:0:0:0:0:0:0:1","header":{"content-length":"97","postman-token":"612a69b2-6e8d-4ccd-97ad-5823dc0c703d","host":"localhost:8000","content-type":"application/json","connection":"keep-alive","accept-encoding":"gzip, deflate, br","user-agent":"PostmanRuntime/7.28.4","accept":"*/*"},"params":{},"payload":{"phoneNumber":"080912312302","pin":"[****]","email":"users@astrapay.com"},"payloadSize":89,"method":"POST","contentType":"application/json"}}
{"timestamp":"2021-09-08T15:36:10.904+07:00","version":"1","msg":"ServletDto(uri=/authentication-service/tokens, remoteAddress=0:0:0:0:0:0:0:1, header={content-length=116, postman-token=59cd17f2-d0b7-4a0a-805b-55999bfbc6d6, host=localhost:8000, content-type=application/json, connection=keep-alive, accept-encoding=gzip, deflate, br, user-agent=PostmanRuntime/7.28.4, accept=*/*}, params={}, payload={password=[****], claim=CUSTOMER, userName=080912312302, deviceId=abc123}, payloadSize=106, method=POST, contentType=application/json)","logger":"com.astrapay.starter.configuration.LoggerConfiguration","thread":"http-nio-8000-exec-1","level":"INFO","level_value":20000,"mdc":{"correlation_id":"e1066dfd-bd17-4626-9569-14ec4d76947d"},"request":{"uri":"/authentication-service/tokens","remoteAddress":"0:0:0:0:0:0:0:1","header":{"content-length":"116","postman-token":"59cd17f2-d0b7-4a0a-805b-55999bfbc6d6","host":"localhost:8000","content-type":"application/json","connection":"keep-alive","accept-encoding":"gzip, deflate, br","user-agent":"PostmanRuntime/7.28.4","accept":"*/*"},"params":{},"payload":{"password":"[****]","claim":"CUSTOMER","userName":"080912312302","deviceId":"abc123"},"payloadSize":106,"method":"POST","contentType":"application/json"}}
{"timestamp":"2021-09-08T15:31:01.204+07:00","version":"1","msg":"ServletDto(uri=/authentication-service/credentials/3/pin, remoteAddress=0:0:0:0:0:0:0:1, header={postman-token=e7f5276e-70bd-4a21-a28c-e1312d450bb1, host=localhost:8000, connection=keep-alive, accept-encoding=gzip, deflate, br, user-agent=PostmanRuntime/7.28.4, accept=*/*}, params={compare=[****], flow=flow}, payload={}, payloadSize=0, method=GET, contentType=null)","logger":"com.astrapay.starter.configuration.LoggerConfiguration","thread":"http-nio-8000-exec-5","level":"INFO","level_value":20000,"mdc":{"correlation_id":"9c7628ea-236f-4b48-a847-02d293ede802"},"request":{"uri":"/authentication-service/credentials/3/pin","remoteAddress":"0:0:0:0:0:0:0:1","header":{"postman-token":"e7f5276e-70bd-4a21-a28c-e1312d450bb1","host":"localhost:8000","connection":"keep-alive","accept-encoding":"gzip, deflate, br","user-agent":"PostmanRuntime/7.28.4","accept":"*/*"},"params":{"compare":"[****]","flow":"flow"},"payload":{},"payloadSize":0,"method":"GET","contentType":null}}
{"timestamp":"2024-07-03T15:49:20.334+07:00","version":"1","msg":"ServletDto(uri=/notification-service/sms, remoteAddress=0:0:0:0:0:0:0:1, header={content-length=187, postman-token=a0660c33-7c06-4330-bf9a-6c492b874643, host=localhost:2929, content-type=application/json, connection=keep-alive, cache-control=no-cache, accept-encoding=gzip, deflate, br, user-agent=PostmanRuntime/7.39.0, accept=*/*}, params={}, payload={phoneNumber=081383771744, data={pin=[****], otp=[****]}, claim=LOGIN, idTemplate=5f616a84-6262-11ee-8c99-0242ac120002}, payloadSize=179, method=POST, contentType=application/json)","logger":"com.astrapay.starter.configuration.LoggerConfiguration","thread":"http-nio-2929-exec-1","level":"INFO","level_value":20000,"mdc":{"correlation_id":"51f37d79-435c-409b-b8d5-29f958a04550"},"request":{"uri":"/notification-service/sms","remoteAddress":"0:0:0:0:0:0:0:1","header":{"content-length":"187","postman-token":"a0660c33-7c06-4330-bf9a-6c492b874643","host":"localhost:2929","content-type":"application/json","connection":"keep-alive","cache-control":"no-cache","accept-encoding":"gzip, deflate, br","user-agent":"PostmanRuntime/7.39.0","accept":"*/*"},"params":{},"payload":{"phoneNumber":"081383771744","data":{"pin":"[****]","otp":"[****]"},"claim":"LOGIN","idTemplate":"5f616a84-6262-11ee-8c99-0242ac120002"},"payloadSize":179,"method":"POST","contentType":"application/json"}}
```


### Common Use

#### URIFormat
```
   objectMapper.convertValue(merchantQrRequestDto, UriFormat.class)
```

#### PageResponse
```
   ParameterizedTypeReference<PageResponse<MerchantQrDto>> responseType = new ParameterizedTypeReference<PageResponse<MerchantQrDto>>() { };
      ResponseEntity<PageResponse<MerchantQrDto>> accountDtoResponseEntity =  restTemplate.exchange(
              merchantQrListEndPoint + objectMapper.convertValue(merchantQrRequestDto, UriFormat.class)
              , HttpMethod.GET,
              null,
              responseType
      );
```
### Serializer dan Deserializer
Gunakan untuk melakukan serealisasi dan deserealisasi tanggal dan waktu LocalDateTime atau ZonedDateTime

```aidl
   @Getter
   @Setter
   public abstract class AuditableDto<T extends RepresentationModel<? extends T>> extends RepresentationModel<T> {
   
       private String createdBy;
   
       @JsonDeserialize(using = LocalDateTimeDeserializer.class)
       @JsonSerialize(using = LocalDateTimeSerializer.class)
       private LocalDateTime createdDate;
       private String lastModifiedBy;
   
       @JsonDeserialize(using = ZonedDateTimeDeserializer.class)
       @JsonSerialize(using = ZonedDateTimeSerializer.class)
       private ZonedDateTime lastModifiedDate;
   }
```

### Indonesia Date Time Serializer & Deserializer
Digunakan untuk serialisasi dan deserialisasi tanggal dan waktu Indonesia (+07:00).
```
   @Getter
   @Setter
   public class MerchantPartnerGroupDto extends AuditableDto<MerchantPartnerGroupDto> {
   
       private Long id;
   
       private String name;
   
       private Boolean active;
   
       @JsonDeserialize(using = IndonesiaDateTimeDeserializer.class)
       @JsonSerialize(using = IndonesiaDateTimeSerializer.class)
       private LocalDateTime registerDate;
   }
```
```
   // request
   {
       "registerDate": "2022-01-24T13:01:02.123Z"
   }
   
   // response
   {
       "registerDate": "2022-01-24T20:01:02.123Z"
   }
```

### Code Generator
Digunakan untuk membuat code dengan random alphanumeric.

- GetRandomAlphaNumeric: digunakan untuk mendapatkan nomer random berdasarkan prefix
  <br/>
`String randomAlphaNumeric = codeGeneratorService.getRandomAlphaNumeric(<custom prefix>);`
  <br/><br/>
- GenerateCode: Digunakan untuk mendapatkan No.Transaksi dengan custom prefix dan Random Number dengan default panjang 30<br/>
`String code = codeGeneratorService.generateCode(<custom prefix>);`
  <br/><br/>
- GenerateInvoiceNumber: Digunakan untuk mendapatkan No.Transaksi dengan prefix INV dan dilanjutkan dengan custom prefix, serta Random Number dengan panjang 30<br/> 
`String transactionNumber = codeGeneratorService.generateInvoiceNumber(<prefix without INV>);`

### Orika Data Converter
1. AdditionalDataToListConverter, <br/> Untuk melakukan convert dari AdditionalData ke List yang akan digunakan oleh Mobile

- Create Source Class with AdditionalData
<details>
    <summary>
        <strong>MyHistoryDetail.java</strong>
    </summary>
<p>

```java
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MyHistoryDetail {

  private Long transactionHeaderId;
  private MyHistoryDetailAdditionalDataDto myHistoryDetailAdditionalDataDto;
}
```
</p>
</details>

- Create Class with implements DetailAdditionalData
<details>
    <summary>
        <strong>MyHistoryDetailAdditionalDataDto.java</strong>
    </summary>
<p>

```java
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MyHistoryDetailAdditionalDataDto implements DetailAdditionalData {

    @AdditionalData(label = "No Transaksi")
    private String transactionNumber;

    @AdditionalData(label = "No Referensi")
    private String referenceNumber;
}
```
</p>
</details>

- Create Target Class with property ArrayList<AdditionalDataDto>
<details>
    <summary>
        <strong>MyHistoryDetailDto.java</strong>
    </summary>
<p>

```java
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MyHistoryDetailDto {

  private Long id;
  private ArrayList<AdditionalDataDto> detailAdditionalData;
}
```
</p>
</details>

- Create Class Configuration Mapper with @Component and implements OrikaMapperFactoryConfigurer
<details>
    <summary>
        <strong>MyHistoryDetailToMyHistoryDetailDtoMapper.java</strong>
    </summary>
<p>

```java
@Component
public class MyHistoryDetailToMyHistoryDetailDtoMapper implements OrikaMapperFactoryConfigurer {
    @Override
    public void configure(MapperFactory mapperFactory) {
        mapperFactory.classMap(MyHistoryDetail.class, MyHistoryDetailDto.class)
                .fieldMap("myHistoryDetailAdditionalDataDto", "detailAdditionalData")
                    .converter(OrikaConverter.AdditionalDataToListConverter.getName()).add()
                .byDefault().register();
    }
}
```
</p>
</details>

- Use MapperFacade in any level Class, then JSON Object created
<details>
    <summary>
        <strong>ExampleController.java</strong>
    </summary>
<p>

```java
MyHistoryDetail myHistoryDetail = generalTransactionService.getTransactionById(transactionId);
return mapperFacade.map(myHistoryDetail, MyHistoryDetailDto.class);
```
</p>
</details>

<details>
    <summary>
        <strong>Example Result.json</strong>
    </summary>
<p>

```json
{
    "id": null,
    "detailAdditionalData": [
        {
            "key": "transactionNumber",
            "value": "INV/QRI/003/220216/110456BMNTS",
            "label": "No Transaksi"
        },
        {
            "key": "referenceNumber",
            "value": "null",
            "label": "No Referensi"
        }
    ]
}
```
</p>
</details>

### AstraPay Signature Generator

Signature adalah sebuah string yang digunakan untuk memverifikasi bahwa
request yang dikirimkan oleh host adalah asli dan tidak tersusupi oleh
hacker. Digunakan untuk menghasilkan AstraPay signature, lalu dibandingkan signature 
yang dikirimkan oleh Host. Jika tidak sama, maka API akan memberikan response 400 dengan
message signature not match.

Ada beberapa elemen yang dibutuhkan untuk generate signature
ini, diantaranya :

    AstrapaySignature :

    1. HttpMethod, example : POST,GET
    2. url, example : http://localhost:8020/disbursement-service/inquiries
    3. key, adalah x-astrapay-key, didapatkan dari Bearer token oleh Host, example : eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJlVm9kSlYzeVN6aUlyLW9aZjZCQzJtUVVaTmJFLWFaQndETjFUUmxmZE1jIn0.eyJleHAiOjE2Mzk0NDY3NzksImlhdCI6MTYzOTQ0NjQ3OSwianRpIjoiMzUxYWVlYTAtNTE2Ny00OTVkLWJhNTYtYWY5NmRhOTg2ZTFjIiwiaXNzIjoiaHR0cDovLzE3Mi4yMC4zLjEyNDo4MDgwL2F1dGgvcmVhbG1zL2FzdHJhcGF5LWJ1c2luZXNzIiwiYXVkIjoiYWNjb3VudCIsInN1YiI6IjJlNjJmNzVhLTA2Y2UtNGQ5ZC04NTRkLTdiYWYxYjk0OWJiOSIsInR5cCI6IkJlYXJlciIsImF6cCI6IjYwMDY4YzlmLTkyMGMtNGVlMy05M2Q5LWQ0NWRlMzcxZjJlMSIsImFjciI6IjEiLCJyZWFsbV9hY2Nlc3MiOnsicm9sZXMiOlsiZGVmYXVsdC1yb2xlcy1hc3RyYXBheS1idXNpbmVzcyIsIm9mZmxpbmVfYWNjZXNzIiwidW1hX2F1dGhvcml6YXRpb24iXX0sInJlc291cmNlX2FjY2VzcyI6eyJhY2NvdW50Ijp7InJvbGVzIjpbIm1hbmFnZS1hY2NvdW50IiwibWFuYWdlLWFjY291bnQtbGlua3MiLCJ2aWV3LXByb2ZpbGUiXX19LCJzY29wZSI6InByb2ZpbGUgZW1haWwiLCJlbWFpbF92ZXJpZmllZCI6ZmFsc2UsImNsaWVudEhvc3QiOiIxNzIuMjAuMy4xMjMiLCJjbGllbnRJZCI6IjYwMDY4YzlmLTkyMGMtNGVlMy05M2Q5LWQ0NWRlMzcxZjJlMSIsInByZWZlcnJlZF91c2VybmFtZSI6InNlcnZpY2UtYWNjb3VudC02MDA2OGM5Zi05MjBjLTRlZTMtOTNkOS1kNDVkZTM3MWYyZTEiLCJjbGllbnRBZGRyZXNzIjoiMTcyLjIwLjMuMTIzIn0.THD7R67KhJu93x4pBzQUSbxb4riGTROdPCFiVTGhz9v1sYmo7Ku1rDNrRw2hZcnnIxrXMPlVsHeLSrjS_gpXFUTSibqGlpqKpQ-niHxWnzHns-IbUAZF0Us0pj_7Yx3g0Q7huGwyV-ZyMXYrgZFCul4IKloUgEkYASznGGM2dCuH9kp90tGwXfQwLsgmq570mtE9LpSS0lIg92uLd8H7nDTCAhQVgG-9r5RkuxJv7GZqcajD2G4hrZgkR8FrtY5gnMu-mA4GzWGfpVwFWqRY_i9y2_nP5attQ07IsbFaARR6SKnMUAVG11mtRopDn8b4MTLpcAJ_IpVYRaQ8KcrZ_Q
    4. bodyClean, adalah request body dengan format JSON yang sudah compacted / minify, dan sudah di hash dengan SHA-256.
    Example : 
        compacted JSON request body : {"phoneNumber":"085225843522","amount":12000}
        SHA256 hashed : 3351ba0056468c402411c673e4266b37706d0d0f98512a933ca57a2c780680ce
    5. timestamp, adalah timestamp dengan format ISO8601 format dan UTC
    Example : 2019-06-21T11:37:51.436Z

    astrapayValidationKey :
    String yang terdiri dari userId+clientId ,lalu di-encode dengan base64.
    Example : 
    10000147cec7b771-0e22-4bd3-b920-8dbb1c232dfa (sebelum di-encode)
    MTAwMDAxNDdjZWM3Yjc3MS0wZTIyLTRiZDMtYjkyMC04ZGJiMWMyMzJkZmE= (setelah di-encode)

### Authentication Configuration

Terdapat 3 file pada konfigurasi autentikasi ini:

    1. AppAuthenticationManager : merupakan Custom Authentication Manager yang berfungsi untuk melakukan verifikasi credential dan authority user
    2. AuthenticationProcessingFilter : digunakan untuk mengambil informasi dari header yaitu user id dan privilege dari user untuk selanjutnya digunakan dalam proses autentikasi oleh AppAuthenticationManager.
    3. AppBasicAuthenticationEntryPoint : digunakan untuk memberikan respon error ketika terdapat user yang tidak berautentikasi mencoba untuk mengakses endpoint terkait.

Ketiga file tersebut akan dipanggil di Security Configuration pada masing-masing service 

    @Configuration
    @EnableWebSecurity
    @EnableGlobalMethodSecurity(prePostEnabled = true)
    public class WebSecurity {
        
        @Autowired
        private AppBasicAuthenticationEntryPoint appBasicAuthenticationEntryPoint;

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
            http
                    .addFilterBefore(getFilter(), AnonymousAuthenticationFilter.class)
                    .exceptionHandling().authenticationEntryPoint(appBasicAuthenticationEntryPoint);
            return http.getOrBuild();
        }
        
        private RequestMatcher getRequestMatchers() {
            return new OrRequestMatcher(new AntPathRequestMatcher("/**"));
        }
    
        private Filter getFilter() {
            return new AuthenticationProcessingFilter(getRequestMatchers(), new AppAuthenticationManager());
        }

Setelah menambahkan ketiga file tersebut ke Security Configuration, selanjutnya kita dapat menambahkan penjagaan akses endpoint berdasarkan privilege tertentu. Ini dapat kita lakukan dengan menggunakan @PreAuthorize

    @GetMapping("/me/transactions")
    @PreAuthorize("hasAuthority('PRIVILEGE.CODE.HERE')")
    public Page getTransactions( TransactionRequestDto transactionRequestDto, Pageable pageable) throws NotFoundException {
    return service.getMyTransactions(transactionRequestDto, pageable);
    }

#### Bagaimana cara meng-setup master-slave ?
Anda perlu menambah ke-4 field di bawah berikut di setiap masing masing properties sesuai environment (application-sit.properties, application-uat.properties, application-prod.properties)
    
    slave.spring.datasource.url={your slave database url}
    slave.spring.datasource.username={your slave database username}
    slave.spring.datasource.password={your slave database password}
    slave.spring.datasource.driver-class-name=org.postgresql.Driver

untuk slave.spring.datasource.url, slave.spring.datasource.username, slave.spring.datasource.password Anda bisa membuat sama seperti Master database url, username dan passowrd di dalam 
application-sit.properties dan application-uat.properties jika SIT dan UAT environment tidak memiliki slave database

##### lalu kamu perlu menambahkan value berikut di application.properties
    master-slave-configuration.enabled=true


### SNAP BI Signature Generator
1. Signature Access Token<br /> 
Digunakan untuk generate `Signature` yang nantinya digunakan untuk mengakses **API Access Token**.
2. Signature Service<br />
Digunakan untuk generate `Signature` yang nantinya digunakan untuk mengakses **API Service**.<br />
Signature Service memiliki dua method :
``` java
public String generateSignature(Map<String, Object> requestBody, SignatureDto signatureDto, String clientSecret) {}
```

``` java
public String generateSignature(SignatureBodyDto requestBody, SignatureDto signatureDto, String clientSecret) {}
```

Method kedua meminta argument abstract class `SignatureBodyDto.class` sehingga requestBody harus di-extends terlebih dahulu. Contoh:

```java
@Getter
@Setter // gunakan @Getter @Setter daripada @Data
static class AccountInquiry extends SignatureBodyDto {
private String partnerReferenceNo;
private String beneficiaryAccountNo;
}
```

`SignatureBodyDto.class` memiliki override method `toString()` yang berfungsi mengkonversi object ke json string secara teratur dan berurutan.

### Konfigurasi BigQuery

1. Anda perlu menambahkan ke-3 field dibawah berikut di setiap masing masing properties sesuai dengan environment (application-sit.properties, application-uat.properties, application-prod.properties)

```properties
spring.cloud.gcp.project-id={digunakan untuk menentukan ID pada GCP, contoh (astrapay-sit)}
spring.cloud.gcp.credentials.location={digunakan untuk menentukan lokasi file JSON yang berisi informasi kredensial yang diperlukan untuk mengakses layanan GCP, contoh (file:/Users/Astrapay/Desktop/astrapay-sit-9cb9e492687f.json)}
spring.cloud.gcp.bigquery.dataset-name= {digunakan untuk menentukan nama dataset pada BigQuery, contoh (sit_astrapay_staging)}
```

2. Selanjutnya kamu perlu menambahkan value berikut di application.properties jika belum menggunakan BigQuery

```properties
spring.cloud.gcp.bigquery.enabled=false
```

3. Jika sudah menggunakan bigquery value di application properties harus diubah menjadi "true"

```properties
spring.cloud.gcp.bigquery.enabled=true
```

4. Implementasi BigQuery di domain, dengan melakukan extends class BigQueryService dari astrapay starter pada service yang kalian inginkan sebagai service untuk mengelola BigQuery. Object T digunakan sebagai object dto yang akan dipakai sebagai hasil yang diinginkan dari service ini, sementara Object V adalah dto class yang akan digunakan untuk menampung hasil dari BigQuery 

```java
public abstract class BigQueryService<T, V> {}
```
### AdditionalDataList
1. AdditionalDataList adalah objek yang memungkinkan kita untuk menambahkan informasi tambahan ke payload dinamis
2. Fungsi ini digunakan untuk mengambil objek HibernateConstraintValidatorContext dari ConstraintValidatorContext pada saat validasi konstrain sedang berlangsung. Objek HibernateConstraintValidatorContext menyediakan metode tambahan yang berkaitan dengan Hibernate, yang dapat digunakan untuk mengatur payload dinamis yang akan digunakan dalam pesan kesalahan validasi.

```properties
HibernateConstraintValidatorContext hibernateConstraintValidatorContext = context.unwrap(HibernateConstraintValidatorContext.class);
```
3. AdditionalDataList adalah objek yang memungkinkan kita untuk menambahkan informasi tambahan ke payload dinamis
```properties
AdditionalDataList additionalDataList = AdditionalDataList.builder().build();
additionalDataList.add("retryCount", String.valueOf(otpAttempt.getAttemptCount()));
additionalDataList.add("retryIn", null);
```
4. Metode withDynamicPayload() untuk menambahkan payload dinamis ke dalam konteks validasi.
5. hibernateConstraintValidatorContext.withDynamicPayload(additionalDataList) digunakan untuk menambahkan additionalDataList ke payload dinamis. AdditionalDataList adalah objek yang memungkinkan kita untuk menambahkan informasi tambahan ke payload dinamis
```properties
hibernateConstraintValidatorContext.withDynamicPayload(additionalDataList);
```
5. Contoh Sederhana Penggunaan AdditionalDataList dimana contoh ini menambahkan informasi tambahan mengenai berapa jumlah retryCount dan retryIn dari request submit OTP
```properties
  @Override
  public boolean isValid(Object[] parameters, ConstraintValidatorContext context) {
    HibernateConstraintValidatorContext hibernateConstraintValidatorContext = context.unwrap(HibernateConstraintValidatorContext.class);

    TokenOtpRequestDto otpRequestDto = (TokenOtpRequestDto) parameters[SECOND_PARAMETERS];

    OtpAttempt otpAttempt = validationOtpService.findAttemptByPhoneNumber(otpRequestDto.getUsername());

    if (!Objects.isNull(otpAttempt.getLockExpired()) && otpAttempt.getLockExpired().isAfter(LocalDateTime.now())) {
      AdditionalDataList additionalDataList = AdditionalDataList.builder().build();
      additionalDataList.add("retryCount", String.valueOf(otpAttempt.getAttemptCount()));
      additionalDataList.add("retryIn", String.valueOf(ChronoUnit.SECONDS.between(LocalDateTime.now(), otpAttempt.getLockExpired())));

      hibernateConstraintValidatorContext.withDynamicPayload(additionalDataList);

      return false;
    }
    return true;
  }
```

### Customer Error Code
1. Customer Error Code digunakan bilamana ada keperluan untuk memberikan informasi error code yang di buat custom untuk memudahkan front-end dalam mengkonsumsi response yang diberikan oleh back-end
2. Cara Penggunaan dengan menambahan atribute code pada @interface validator
```properties
@Documented
@Constraint(validatedBy = { PasswordAttemptTemporaryLockedValidator.class })
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordAttemptTemporaryLocked {

  String code() default "PIN_SUBMIT_TEMPORARY_LOCK";

  String message() default "Verification attempt limit exceeded!";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

}
```
3. Output
```properties
{
    "status": 400,
    "message": "Validation failed for tokenRequestDto(). Error count 1",
    "error": "Bad Request",
    "path": "/authentication-service/v1/tokens",
    "timestamp": "2023-06-12T17:15:19.592772",
    "details": [
        {
            "code": "PIN_SUBMIT_TEMPORARY_LOCK",
            "objectName": "tokenRequestDto",
            "defaultMessage": "Verification attempt limit exceeded!",
            "field": "",
            "rejectedValue": {
                "username": "082235401113",
                "password": "111112",
                "refreshToken": null,
                "authCode": null,
                "uniqueId": null,
                "claim": "LOGIN",
                "grantType": "PIN"
            },
            "additionalData": [
                {
                    "key": "retryCount",
                    "value": "3"
                },
                {
                    "key": "retryIn",
                    "value": "298"
                }
            ]
        }
    ]
}
```

### @PreAuthorize menggunakan PrivilegeVerifierService
> Library ini sudah dapat digunakan di versi astrapay starter 1.14.50 ke atas
> 
> Notes : Pada endpoint yang akan dilakukan implementasi @PreAuthorize menggunakan anotasi @Valid di controller, maka perlu dilakukan perubahan code dengan memindahkan anotasi @Valid di level controller ke level service (menggunakan anotasi @Validated di kelas service & @Valid di dalam suatu method di dalam service)

Terdapat tiga buah class yang akan terbentuk pada setiap project apabila menggunakan astrapay-starter version 1.14.49 ke atas

    1. PrivilegeVerifierService dengan beanName privilegeVerifierService : merupakan service yang memverifikasi apakah seorang user yang dapat diidentifikasi dengan x-user-id memiliki sebuah privilege dengan privilege code tertentu dengan melakukan komunikasi rest client ke Authentication Service endpoint me/privileges/{privilegeCode}
    2. PrivilegeVerifierClient dengan beanName privilegeVerifierClient : merupakan rest client yang bertugas untuk melakukan komunikasi dengan Authentication Service terkhusus untuk endpoint me/privileges/{privilegeCode}
    3. RestTemplate dengan beanName starterRestTemplate : merupakan rest template yang akan digunakan untuk kebutuhan rest client di library starter

Dengan memanfaatkan service diatas, kita dapat menambahkan penjagaan akses endpoint pada controller berdasarkan privilegeCode tertentu. 

Cara implementasi:

1. Menambahkan properties untuk url Authentication Service pada project dan isi sesuai environment


    astrapay-starter.authentication.base.url={baseUrl}
    astrapay-starter.authentication.endpoint.verifyMyPrivilegeCode=/me/privileges/{privilegeCode}

2. Menambahkan anotasi @PreAuthorize dengan memanggil method @privilegeVerifierService.hasPrivilege(#privilegeCode) dengan parameter privilegeCode sesuai yang terdaftar di table privilege. 


    @GetMapping("/me/transactions")
    @PreAuthorize("@privilegeVerifierService.hasPrivilege('DOMAIN.MODULE.ACTION')")
    public Page getTransactions( TransactionRequestDto transactionRequestDto, Pageable pageable) throws NotFoundException {
        return service.getMyTransactions(transactionRequestDto, pageable);
    }


## Enkripsi & Dekripsi Objek

Kelas `ObjectEncryption.java` menyediakan metode untuk mengenkripsi dan mendekripsi objek. Kelas ini menggunakan `BytesEncryptor` dan `ObjectMapper` untuk melakukan operasi ini.

Berikut adalah penjelasan singkat tentang metode dalam kelas ini:

1. `encrypt(T object)`: Metode ini menerima objek yang akan dienkripsi. Pertama, objek diubah menjadi string JSON menggunakan `ObjectMapper`. Kemudian, string JSON ini dienkripsi menggunakan `BytesEncryptor` dan hasil enkripsi (dalam bentuk byte[]) diubah menjadi string heksadesimal yang kemudian dikembalikan.

2. `decrypt(String encryptedText, Class<T> objectType)`: Metode ini menerima string heksadesimal yang merupakan teks terenkripsi dan kelas dari objek yang akan didekripsi. Pertama, string heksadesimal diubah menjadi byte[] dan didekripsi menggunakan `BytesEncryptor`. Hasil dekripsi (dalam bentuk byte[]) diubah menjadi string dan kemudian diubah kembali menjadi objek menggunakan `ObjectMapper`.

Untuk menggunakan enkripsi dan dekripsi AES, Berikut adalah contoh penggunaannya:

```java
private final ObjectEncryption<String> objectEncryption;

String encryptedText = objectEncryption.encrypt("Hello, World!");
String decryptedText = objectEncryption.decrypt(encryptedText);
```

`Optional`
Jika ingin mengubah salt / password default, silahkan tambah key value di application.properties dengan konfigurasi berikut:
```properties
# AES Configuration
encryption.aes.salt={salt}
encryption.aes.password={password}
```

## Internationalization Service

Kelas `I18nService` menyediakan metode untuk mengelola pesan internasionalisasi (i18n) dalam aplikasi. Kelas ini menggunakan `MessageSource` untuk mengambil pesan yang sesuai dengan locale yang diberikan.

### Metode Utama

**`getMessage(String code)`**: Mengambil pesan berdasarkan kode pesan di .properties

### Contoh Penggunaan

```java
private final I18nService i18nService;

String message = i18nService.getMessage("greeting.message");
System.out.println(message); // Output: Selamat datang
```

### Konfigurasi

Pastikan file `messages.properties` untuk bahasa indonesia (default) dalam aplikasi Spring Boot Anda sudah ada, untuk mendukung pesan internasionalisasi. Contoh konfigurasi:

```properties
greeting.message=Selamat datang
```

dan juga buat file `messages_en.properties` untuk bahasa inggris. Contoh konfigurasi:
```properties
greeting.message=Welcome
```

### Penggunaan Pesan Internasionalisasi
Accept-Language header dapat digunakan untuk menentukan bahasa yang digunakan. Berikut adalah contoh penggunaan:

```java
Accept-Language: en
```
jika tidak ada Accept-Language header, maka bahasa default yang digunakan adalah bahasa Indonesia.

Dengan menggunakan `I18nService`, Anda dapat dengan mudah mengelola dan mengambil pesan internasionalisasi dalam aplikasi Anda.
