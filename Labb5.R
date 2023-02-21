source("https://fileadmin.cs.lth.se/cs/Education/EDAA35/R_resources.R")

medelvarden <- c()
lower <- c()
upper <- c()

# function for plotting data
plotresult <- function(file, start = 1) {
   data <- read.csv(file)
   data <- data[start:nrow(data), ]
   plot(data, type = "l")
}

for (i in 1:10) {
   system("java -Xint Lab data2.txt result1.txt 600")
   plotresult("result1.txt") # plot to screen
   pdf("result1.pdf")
   plotresult("result1.txt") # plot to pdf file
   dev.off()

   #filtrera data
   data <- read.csv("result1.txt")
   fd <- filtered_data(0, 40, data)

   #räkna ut medelvärde
   mv <- medelvarde(fd)

   #registrera medelvärde i vektor
   medelvarden[length(medelvarden) + 1] <- mv

   #räkna ut konfidensintervall
   ki <- konfidensintervall(fd)

   #registrera nedre konfidensintervall i vektor
   lower[length(lower) + 1] <- ki[1]

   #registrera nedre konfidensintervall i vektor
   upper[length(upper) + 1] <- ki[2]

   print(i)
}

#resultat i dataframe
data <- data.frame(medelvärden = medelvarden, nedre = lower, övre = upper)

write.csv(data, "C:\\Users\\leofj\\skola\\Utvärdering av programvarusystem\\labb5\\Xint_mergeSort_10_600_data2.csv", row.names=FALSE)

#filtrera datan
filtered_data <- function(start, end, data) {
   return(data[-c(start:end), ])
}

#medelvärde
medelvarde <- function(data) {
   return(mean(data[, 2]))
}

#konfidensintervall
konfidensintervall <- function(data) {
   return(confidenceInterval(data[, 2], confidenceLevel=0.95))
}