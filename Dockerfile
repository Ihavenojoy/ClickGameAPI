# Use the official .NET SDK image to build the app
FROM mcr.microsoft.com/dotnet/sdk:7.0 AS build
WORKDIR /app

# Copy .csproj and restore dependencies
COPY *.sln .
COPY ClickGameAPI/*.csproj ./ClickGameAPI/
RUN dotnet restore

# Copy the rest of the source code
COPY . .
WORKDIR /app/ClickGameAPI
RUN dotnet publish -c Release -o /out

# Build runtime image
FROM mcr.microsoft.com/dotnet/aspnet:7.0 AS runtime
WORKDIR /app
COPY --from=build /out ./

# Expose port 80 (or your API port)
EXPOSE 80

ENTRYPOINT ["dotnet", "ClickGameAPI.dll"]
