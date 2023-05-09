# SimpleClient

[![GitHub Release](https://img.shields.io/github/v/release/mgolcu00/SimpleClient)](https://github.com/mgolcu00/SimpleClient/releases)
[![License](https://img.shields.io/github/license/mgolcu00/SimpleClient)](https://github.com/mgolcu00/SimpleClient/blob/main/LICENSE)

SimpleClient, Java tabanlı projelerinizde hızlı ve kolayca HTTP istekleri yapmanızı sağlayan bir kütüphanedir. Basit ve anlaşılır bir API sunarak, projelerinizde hızlıca entegre edebilir ve kullanmaya başlayabilirsiniz.

## Özellikler

- GET ve POST istekleri için senkron ve asenkron yöntemler
- Kolayca kullanılabilir ve özelleştirilebilir
- Genişletilebilir yapı

## Başlarken

### Gereksinimler

- Java 8 veya üzeri sürüm

### Kurulum

Maven projenize SimpleClient'ı eklemek için, `pom.xml` dosyanıza şu bağımlılığı ekleyin:

```xml
<dependency>
  <groupId>io.github.mgolcu00</groupId>
  <artifactId>simpleclient</artifactId>
  <version>{version}</version>
</dependency>
````

## Kullanım
SimpleClient ile çalışmaya başlamak için önce bir SimpleClient nesnesi oluşturun ve ardından istediğiniz HTTP isteğini gerçekleştirebilirsiniz.
```JAVA
import com.mertgolcu.simpleclient.SimpleClient;

public class Main {
    public static void main(String[] args) {
        SimpleClient client = new SimpleClient();

        // İstediğiniz HTTP isteğini gerçekleştirin
    }
}
`````

## GET
```JAVA 
String url = "https://jsonplaceholder.typicode.com/posts/1";
client.get(url, String.class, response -> {
    if (response.isSuccessful()) {
        System.out.println(response.getBody());
    } else {
        System.out.println("Hata: " + response.getError());
    }
});
`````

## POST
```JAVA
String url = "https://jsonplaceholder.typicode.com/posts";
Foo request = new Foo("foo",100);

client.post(url, request, Foo.class, response -> {
    if (response.isSuccessful()) {
        System.out.println(response.getBody());
    } else {
        System.out.println("Hata: " + response.getError());
    }
});
`````


