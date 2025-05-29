# 🧠 BFHL Webhook Submission Service

This is a Spring Boot project that interacts with the **BFHL Webhook API**. It performs the following actions:

1. Registers a user and generates a webhook.
2. Submits a SQL query to the generated webhook using the access token.

---

## 🚀 Features

- Uses `RestTemplate` to make HTTP POST requests.
- Handles relative/absolute URLs for the webhook.
- Logs each step clearly for debugging.
- Submits a predefined SQL query to a secured webhook.

---

## 🛠️ Prerequisites

Ensure you have the following installed on your system:

- [Java 17+](https://adoptopenjdk.net/)
- [Maven 3.6+](https://maven.apache.org/install.html)
- [Git](https://git-scm.com/) (optional but recommended)
- An IDE like IntelliJ IDEA or VS Code (optional)

---

## 🛠️ Installation & Setup

1. **Clone the repository**
   ```bash
   git clone https://github.com/your-username/bfhl-webhook-service.git
   cd bfhl-webhook-service

   ```

2. **Build Project**
   ```bash
   mvn clean install

   ```

3. **Run Application**
   ```bash
   mvn spring-boot:run

   ```

4. **Output Comes out Like this**
   🔍 Response Status Code: 200 OK
   🔍 Response Body: {webhook=..., accessToken=...}
   ✅ Submitting to webhook URL: ...
   ✅ Webhook Response: ...

----
📥 **Direct JAR Download (Raw Link):**  
https://github.com/badoni003shreyansh/BHFL_Answer/releases/download/v1.0.0/bfhl-webhook-service-1.0.0.jar
