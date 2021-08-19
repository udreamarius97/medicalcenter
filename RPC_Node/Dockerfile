FROM node:12
 WORKDIR /app
 COPY package.json /app
 RUN npm install
 COPY . /app
 EXPOSE 50051
 EXPOSE 5000
 EXPOSE 8081
 CMD ["node","./dynamic_codegen/server.js"]
 CMD ["node","./dynamic_codegen/server.js"]
 