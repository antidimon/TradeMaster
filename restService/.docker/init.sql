SELECT 'CREATE DATABASE stocksmain'
WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'stocksmain')\gexec