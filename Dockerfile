FROM openjdk:11-jre-slim

# define o diretorio de trabalho no container
WORKDIR /app

# copia de um diretorio local para container
# obs : o nome do jar mode mudar conforme desejar
COPY target/*.jar /app/api.jar

# serve para documentar a porta de conexao ( não publica ela )
EXPOSE 8080

# comandos para ser executados no WORKDIR
CMD ["java", "-jar", "api.jar"]