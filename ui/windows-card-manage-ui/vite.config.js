import { fileURLToPath, URL } from 'node:url'

import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [vue()],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    }
  },
  // 服务配置
  server: {
    host: "0.0.0.0",
    port: 8080,
    open: true
  },
  define: {
    'process.env': {
      VITE_API_URL: "http://localhost:8000"
    }
  }
})
