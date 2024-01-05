import { defineConfig } from "vite";
import vue from "@vitejs/plugin-vue";

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [vue()],
  resolve: {
    alias: [
      { find: "#", replacement: "/src/components" },
      { find: "@", replacement: "/src" },
    ],
  },
  build: {
    outDir: "../../backend/keyboard/src/main/resources/static"
  },
  server: {
    port: 8080,
  }
});
