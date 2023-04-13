version='1.1.0'
SHELL_FOLDER=$(cd "$(dirname "$0")";pwd)
cd ${SHELL_FOLDER}

#编译项目
cd ../
mvn clean package -DskipTests -Pdocker

#编译
docker build -f scripts/docker/Dockerfile -t visual/open-anpr:${version} .
docker tag visual/open-anpr:${version} visual/open-anpr:latest

#中央仓库
docker tag visual/open-anpr:${version} divenswu/open-anpr:${version}
docker tag visual/open-anpr:${version} divenswu/open-anpr:latest