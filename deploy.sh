git pull https://villaflaminio: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@github.com/villaflaminio/geris.git &&

mvn clean install &&
docker stop geris_backend &&
docker rm geris_backend &&
docker build -t geris_backend . &&
docker run -d --name geris_backend -p 1564:1564 geris_backend

