SELECT 'CREATE DATABASE stocksauth'
WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'stocksauth')\gexec