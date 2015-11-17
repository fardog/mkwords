FROM clojure
EXPOSE 3000
RUN mkdir -p /usr/jar/app
WORKDIR /usr/jar/app
COPY target/mkwords.jar .
CMD java -cp mkwords.jar clojure.main -m mkwords.server
