FROM node:16.20.2 AS build

WORKDIR /app
RUN apt-get update

COPY package.json .
RUN npm install

COPY . .
RUN npm run build --prod

FROM nginx:latest

COPY --from=build /app/dist /usr/share/nginx/html
COPY default.conf /etc/nginx/conf.d

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]