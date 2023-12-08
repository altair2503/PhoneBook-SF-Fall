# Start from the official Golang image
FROM golang:latest

# Set the working directory inside the container
WORKDIR /go/src/app

# Copy the Casbin service source code into the container
COPY . .

# Download Casbin and its dependencies using go mod
RUN go mod tidy
RUN go mod download

# Build the Casbin service
RUN go build -o casbin-service .

# Expose the port on which the Casbin service will run
EXPOSE 8080

# Define the command to run the Casbin service when the container starts
CMD ["./casbin-service"]
