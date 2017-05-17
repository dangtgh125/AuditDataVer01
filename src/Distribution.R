# Create a sequence of numbers between 1 and 50 incrementing by 1.
x <- seq(1, 50, by = 1)
# Choose the mean as 2.5 and standard deviation as 0.5.
y <- dnorm(x, mean = 21.791448488142347, sd = 3.8250096527484247)
print(y)
write.csv(y,"output.csv", row.names = FALSE)